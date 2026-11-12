/**
 * RouteWise Global Client Library
 * Handles authentication, UI notifications, session persistence, and shared formatters.
 */

const API = {
  // Session User Management
  getUser: function() {
    const raw = localStorage.getItem('rw_user');
    return raw ? JSON.parse(raw) : null;
  },

  setUser: function(user) {
    if (user) {
      localStorage.setItem('rw_user', JSON.stringify(user));
    } else {
      localStorage.removeItem('rw_user');
    }
  },

  logout: async function() {
    try {
      await fetch('/api/auth/logout', { method: 'POST' });
    } catch (e) {
      console.error(e);
    }
    localStorage.removeItem('rw_user');
    window.location.href = '/';
  },

  // Toast Notification
  showToast: function(message, type = 'success') {
    let container = document.getElementById('toastContainer');
    if (!container) {
      container = document.createElement('div');
      container.id = 'toastContainer';
      container.className = 'toast-container';
      document.body.appendChild(container);
    }

    const toast = document.createElement('div');
    toast.className = `toast toast-${type}`;
    toast.innerHTML = `
      <span style="font-size: 1.1rem;">${type === 'success' ? '✓' : '✕'}</span>
      <span>${message}</span>
    `;
    container.appendChild(toast);

    setTimeout(() => {
      toast.style.opacity = '0';
      toast.style.transform = 'translateY(10px)';
      toast.style.transition = 'all 0.3s ease';
      setTimeout(() => toast.remove(), 300);
    }, 3500);
  },

  // Formatters
  formatCurrency: function(amount) {
    if (amount === undefined || amount === null) return '₹0';
    return '₹' + Number(amount).toLocaleString('en-IN', { maximumFractionDigits: 2 });
  },

  formatTime: function(isoString) {
    if (!isoString) return '--:--';
    const date = new Date(isoString);
    return date.toLocaleTimeString('en-IN', { hour: '2-digit', minute: '2-digit', hour12: true });
  },

  formatDate: function(isoString) {
    if (!isoString) return '';
    const date = new Date(isoString);
    return date.toLocaleDateString('en-IN', { day: 'numeric', month: 'short', year: 'numeric' });
  },

  formatDuration: function(minutes) {
    if (!minutes) return '0m';
    const h = Math.floor(minutes / 60);
    const m = minutes % 60;
    if (h === 0) return `${m}m`;
    if (m === 0) return `${h}h`;
    return `${h}h ${m}m`;
  },

  // Populate Cities into Select Dropdowns
  loadCities: async function(fromSelectId, toSelectId) {
    try {
      const res = await fetch('/api/cities');
      if (!res.ok) return;
      const cities = await res.json();
      
      const fromSel = document.getElementById(fromSelectId);
      const toSel = document.getElementById(toSelectId);

      if (fromSel) {
        const currentVal = fromSel.value;
        fromSel.innerHTML = '<option value="">Select departure city</option>' +
          cities.map(c => `<option value="${c.name}">${c.name} (${c.code || c.state})</option>`).join('');
        if (currentVal) fromSel.value = currentVal;
      }

      if (toSel) {
        const currentVal = toSel.value;
        toSel.innerHTML = '<option value="">Select destination city</option>' +
          cities.map(c => `<option value="${c.name}">${c.name} (${c.code || c.state})</option>`).join('');
        if (currentVal) toSel.value = currentVal;
      }
    } catch (e) {
      console.error('Failed to load cities:', e);
    }
  },

  // Real Photography Assets Catalog (Stored Locally in /images/ for Fast, Persistent Loading on Refresh)
  cityImages: {
    'delhi': '/images/delhi.jpg',
    'chandigarh': '/images/chandigarh.jpg',
    'manali': '/images/manali.jpg',
    'shimla': '/images/shimla.jpg',
    'jaipur': '/images/jaipur.jpg',
    'amritsar': '/images/amritsar.jpg',
    'dehradun': '/images/dehradun.jpg',
    'ludhiana': '/images/ludhiana.jpg',
    'ambala': '/images/ambala.jpg',
    'karnal': '/images/karnal.jpg',
    'default': '/images/chandigarh.jpg'
  },

  busImages: {
    'volvo': '/images/volvo.jpg',
    'sleeper': '/images/sleeper.jpg',
    'scania': '/images/scania.jpg',
    'bharatbenz': '/images/bharatbenz.jpg',
    'mercedes': '/images/bharatbenz.jpg',
    'electric': '/images/electric.jpg',
    'hero': '/images/hero-bus.jpg',
    'default': '/images/volvo.jpg'
  },

  getCityImage: function(cityName) {
    if (!cityName) return this.cityImages['default'];
    const key = cityName.toLowerCase().trim();
    for (let c in this.cityImages) {
      if (key.includes(c)) return this.cityImages[c];
    }
    return this.cityImages['default'];
  },

  getBusImage: function(busType, busName) {
    const key = ((busType || '') + ' ' + (busName || '')).toLowerCase().trim();
    if (key.includes('sleeper')) return this.busImages['sleeper'];
    if (key.includes('electric') || key.includes('ev') || key.includes('zero emission')) return this.busImages['electric'];
    if (key.includes('scania')) return this.busImages['scania'];
    if (key.includes('mercedes') || key.includes('benz')) return this.busImages['bharatbenz'];
    if (key.includes('bharatbenz')) return this.busImages['bharatbenz'];
    if (key.includes('mountain') || key.includes('himalayan')) return this.busImages['scania'];
    if (key.includes('volvo')) return this.busImages['volvo'];
    return this.busImages['volvo'];
  },

  // Navbar Dynamic Authentication Sync
  renderNavbar: function() {
    const user = API.getUser();
    const navUserEl = document.getElementById('navUserSlot');
    if (!navUserEl) return;

    if (user) {
      const isAdm = user.role === 'ROLE_ADMIN';
      const dashboardLink = isAdm ? '/admin/dashboard' : '/passenger/dashboard';
      navUserEl.innerHTML = `
        <div style="display: flex; align-items: center; gap: 10px;">
          <a href="${dashboardLink}" class="btn btn-secondary btn-sm" style="display: flex; align-items: center; gap: 8px;">
            <div style="width: 24px; height: 24px; border-radius: 50%; background: var(--primary); color: #fff; display: flex; align-items: center; justify-content: center; font-size: 0.75rem; font-weight: 700;">
              ${(user.name || 'U').charAt(0).toUpperCase()}
            </div>
            <span>${user.name}</span>
            <span class="badge ${isAdm ? 'badge-peach' : 'badge-green'}" style="font-size: 0.7rem; padding: 2px 7px;">
              ${isAdm ? 'Admin' : 'Passenger'}
            </span>
          </a>
          <button onclick="API.logout()" class="btn btn-outline btn-sm" title="Sign Out">
            <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path>
              <polyline points="16 17 21 12 16 7"></polyline>
              <line x1="21" y1="12" x2="9" y2="12"></line>
            </svg>
          </button>
        </div>
      `;
    } else {
      navUserEl.innerHTML = `
        <div style="display: flex; align-items: center; gap: 10px;">
          <a href="/login" class="btn btn-outline btn-sm">Sign In</a>
          <a href="/register" class="btn btn-primary btn-sm">Register</a>
        </div>
      `;
    }
  }
};

document.addEventListener('DOMContentLoaded', () => {
  API.renderNavbar();
});
