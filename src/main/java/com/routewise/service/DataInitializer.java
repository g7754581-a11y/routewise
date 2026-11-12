package com.routewise.service;

import com.routewise.dto.BookingRequest;
import com.routewise.dto.WaitingListRequest;
import com.routewise.entity.*;
import com.routewise.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final BusStopRepository busStopRepository;
    private final RouteRepository routeRepository;
    private final RouteStopRepository routeStopRepository;
    private final BusRepository busRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleService scheduleService;
    private final BookingService bookingService;
    private final WaitingListService waitingListService;
    private final RouteOptimizationService routeOptimizationService;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           CityRepository cityRepository,
                           BusStopRepository busStopRepository,
                           RouteRepository routeRepository,
                           RouteStopRepository routeStopRepository,
                           BusRepository busRepository,
                           ScheduleRepository scheduleRepository,
                           ScheduleService scheduleService,
                           BookingService bookingService,
                           WaitingListService waitingListService,
                           RouteOptimizationService routeOptimizationService,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.busStopRepository = busStopRepository;
        this.routeRepository = routeRepository;
        this.routeStopRepository = routeStopRepository;
        this.busRepository = busRepository;
        this.scheduleRepository = scheduleRepository;
        this.scheduleService = scheduleService;
        this.bookingService = bookingService;
        this.waitingListService = waitingListService;
        this.routeOptimizationService = routeOptimizationService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (cityRepository.count() > 0) {
            routeOptimizationService.refreshGraph();
            return;
        }

        // =====================================================================
        // 1. Seed Users (1 Admin + 10 Passengers)
        // =====================================================================
        String defaultPassHash = passwordEncoder.encode("pass123");
        String adminPassHash = passwordEncoder.encode("admin123");

        User admin = userRepository.save(new User("System Administrator", "admin@routewise.com", adminPassHash, "+91 98123 45678", Role.ROLE_ADMIN));
        User passenger = userRepository.save(new User("Rahul Verma", "passenger@routewise.com", defaultPassHash, "+91 98765 43210", Role.ROLE_PASSENGER));

        User u2 = userRepository.save(new User("Rohit Sharma", "rohit.sharma@example.com", defaultPassHash, "+91 98234 11223", Role.ROLE_PASSENGER));
        User u3 = userRepository.save(new User("Priya Singh", "priya.singh@example.com", defaultPassHash, "+91 98345 22334", Role.ROLE_PASSENGER));
        User u4 = userRepository.save(new User("Aarav Mehta", "aarav.mehta@example.com", defaultPassHash, "+91 98456 33445", Role.ROLE_PASSENGER));
        User u5 = userRepository.save(new User("Neha Patel", "neha.patel@example.com", defaultPassHash, "+91 98567 44556", Role.ROLE_PASSENGER));
        User u6 = userRepository.save(new User("Vikram Verma", "vikram.verma@example.com", defaultPassHash, "+91 98678 55667", Role.ROLE_PASSENGER));
        User u7 = userRepository.save(new User("Anjali Sharma", "anjali.sharma@example.com", defaultPassHash, "+91 98789 66778", Role.ROLE_PASSENGER));
        User u8 = userRepository.save(new User("Kabir Das", "kabir.das@example.com", defaultPassHash, "+91 98890 77889", Role.ROLE_PASSENGER));
        User u9 = userRepository.save(new User("Simran Kaur", "simran.kaur@example.com", defaultPassHash, "+91 98901 88990", Role.ROLE_PASSENGER));
        User u10 = userRepository.save(new User("Aditya Roy", "aditya.roy@example.com", defaultPassHash, "+91 98012 99001", Role.ROLE_PASSENGER));

        // =====================================================================
        // 2. Seed 10 Cities
        // =====================================================================
        City delhi = cityRepository.save(new City("Delhi", "Delhi", "DEL", 28.6139, 77.2090));
        City chandigarh = cityRepository.save(new City("Chandigarh", "Punjab / Haryana", "CH", 30.7333, 76.7794));
        City jaipur = cityRepository.save(new City("Jaipur", "Rajasthan", "JAI", 26.9124, 75.7873));
        City shimla = cityRepository.save(new City("Shimla", "Himachal Pradesh", "SHM", 31.1048, 77.1734));
        City amritsar = cityRepository.save(new City("Amritsar", "Punjab", "ASR", 31.6340, 74.8723));
        City ludhiana = cityRepository.save(new City("Ludhiana", "Punjab", "LDH", 30.9010, 75.8573));
        City dehradun = cityRepository.save(new City("Dehradun", "Uttarakhand", "DED", 30.3165, 78.0322));
        City manali = cityRepository.save(new City("Manali", "Himachal Pradesh", "MNL", 32.2432, 77.1892));
        City ambala = cityRepository.save(new City("Ambala", "Haryana", "AMB", 30.3782, 76.7767));
        City karnal = cityRepository.save(new City("Karnal", "Haryana", "KRN", 29.6857, 76.9905));

        // Bus stops
        busStopRepository.save(new BusStop(delhi, "ISBT Kashmere Gate, Delhi", "Near Gate 1 Metro"));
        busStopRepository.save(new BusStop(delhi, "Majnu Ka Tilla, Delhi", "Near Tibetan Market"));
        busStopRepository.save(new BusStop(chandigarh, "ISBT Sector 43, Chandigarh", "Platform 12"));
        busStopRepository.save(new BusStop(chandigarh, "Tribune Chowk, Chandigarh", "Highway Flyover"));
        busStopRepository.save(new BusStop(jaipur, "Sindhi Camp Bus Stand, Jaipur", "Main Terminal"));
        busStopRepository.save(new BusStop(shimla, "ISBT Tutikandi, Shimla", "New Bus Stand"));
        busStopRepository.save(new BusStop(amritsar, "Amritsar Bus Stand", "Near Golden Temple Road"));
        busStopRepository.save(new BusStop(ludhiana, "Sherpur Chowk, Ludhiana", "GT Road"));
        busStopRepository.save(new BusStop(dehradun, "ISBT Dehradun", "Haridwar Bypass"));
        busStopRepository.save(new BusStop(manali, "Private Bus Stand, Manali", "Mall Road"));
        busStopRepository.save(new BusStop(ambala, "Ambala Cantt Bus Stand", "Railway Station Road"));
        busStopRepository.save(new BusStop(karnal, "Karnal Toll Plaza / Oasis", "GT Karnal Road"));

        // =====================================================================
        // 3. Seed Routes & Intermediate Stops (North India Network)
        // =====================================================================
        // Delhi <-> Chandigarh
        Route rDelChd = routeRepository.save(new Route(delhi, chandigarh, 245.0, 270, true));
        Route rChdDel = routeRepository.save(new Route(chandigarh, delhi, 245.0, 270, true));
        routeStopRepository.save(new RouteStop(rDelChd, "Karnal Oasis Stop", 1, 125.0, 15));
        routeStopRepository.save(new RouteStop(rDelChd, "Ambala Cantt Junction", 2, 200.0, 10));
        routeStopRepository.save(new RouteStop(rChdDel, "Ambala Cantt Junction", 1, 45.0, 10));
        routeStopRepository.save(new RouteStop(rChdDel, "Karnal Oasis Stop", 2, 120.0, 15));

        // Chandigarh <-> Shimla
        Route rChdShm = routeRepository.save(new Route(chandigarh, shimla, 115.0, 210, true));
        Route rShmChd = routeRepository.save(new Route(shimla, chandigarh, 115.0, 210, true));
        routeStopRepository.save(new RouteStop(rChdShm, "Kalka Bypass", 1, 30.0, 10));
        routeStopRepository.save(new RouteStop(rChdShm, "Solan Highway Stop", 2, 70.0, 15));

        // Delhi <-> Jaipur
        Route rDelJai = routeRepository.save(new Route(delhi, jaipur, 275.0, 310, true));
        Route rJaiDel = routeRepository.save(new Route(jaipur, delhi, 275.0, 310, true));
        routeStopRepository.save(new RouteStop(rDelJai, "Gurugram IFFCO Chowk", 1, 35.0, 10));
        routeStopRepository.save(new RouteStop(rDelJai, "Behror Mid-way", 2, 130.0, 20));

        // Chandigarh <-> Amritsar
        Route rChdAsr = routeRepository.save(new Route(chandigarh, amritsar, 225.0, 260, true));
        Route rAsrChd = routeRepository.save(new Route(amritsar, chandigarh, 225.0, 260, true));
        routeStopRepository.save(new RouteStop(rChdAsr, "Ludhiana Samrala Bypass", 1, 95.0, 15));
        routeStopRepository.save(new RouteStop(rChdAsr, "Jalandhar Rama Mandi", 2, 155.0, 10));

        // Chandigarh <-> Manali
        Route rChdMnl = routeRepository.save(new Route(chandigarh, manali, 305.0, 480, true));
        Route rMnlChd = routeRepository.save(new Route(manali, chandigarh, 305.0, 480, true));
        routeStopRepository.save(new RouteStop(rChdMnl, "Bilaspur Lake View", 1, 135.0, 20));
        routeStopRepository.save(new RouteStop(rChdMnl, "Mandi Town Terminal", 2, 200.0, 20));
        routeStopRepository.save(new RouteStop(rChdMnl, "Kullu Bypass", 3, 265.0, 15));

        // Delhi <-> Dehradun
        Route rDelDed = routeRepository.save(new Route(delhi, dehradun, 250.0, 330, true));
        Route rDedDel = routeRepository.save(new Route(dehradun, delhi, 250.0, 330, true));
        routeStopRepository.save(new RouteStop(rDelDed, "Meerut Expressway Toll", 1, 65.0, 10));
        routeStopRepository.save(new RouteStop(rDelDed, "Roorkee Bypass", 2, 180.0, 15));

        // Local connectivity legs for Graph Dijkstra/BFS/MST
        Route rDelKrn = routeRepository.save(new Route(delhi, karnal, 125.0, 135, true));
        Route rKrnDel = routeRepository.save(new Route(karnal, delhi, 125.0, 135, true));
        Route rKrnAmb = routeRepository.save(new Route(karnal, ambala, 75.0, 80, true));
        Route rAmbKrn = routeRepository.save(new Route(ambala, karnal, 75.0, 80, true));
        Route rAmbChd = routeRepository.save(new Route(ambala, chandigarh, 45.0, 55, true));
        Route rChdAmb = routeRepository.save(new Route(chandigarh, ambala, 45.0, 55, true));
        Route rChdLdh = routeRepository.save(new Route(chandigarh, ludhiana, 95.0, 110, true));
        Route rLdhAsr = routeRepository.save(new Route(ludhiana, amritsar, 130.0, 150, true));

        // =====================================================================
        // 4. Seed 10 Luxury Buses
        // =====================================================================
        Bus b1 = busRepository.save(new Bus("DL01-RW-1001", "RouteWise Royal Volvo 9600 Multi-Axle", "Volvo AC Multi-Axle", 40, "2x2", "High-Speed WiFi, AC, Reading Light, USB Charging, Reclining Seats, Blanket, Mineral Water", true));
        Bus b2 = busRepository.save(new Bus("CH01-RW-2002", "RouteWise Platinum Sleeper Express", "AC Sleeper (2+1)", 30, "2x1", "Individual TV, AC, Blankets, USB Fast Charger, Curtains, Mineral Water", true));
        Bus b3 = busRepository.save(new Bus("HR03-RW-3003", "RouteWise Scania Metroline Luxury", "Scania AC Luxury", 40, "2x2", "Air Suspension, AC, Emergency SOS, Clean Restroom on board, Water Bottle", true));
        Bus b4 = busRepository.save(new Bus("PB65-RW-4004", "RouteWise BharatBenz Glider", "BharatBenz Luxury", 36, "2x2", "Push-back Seats, AC, Reading Lamp, Bottle Holder, USB Port", true));
        Bus b5 = busRepository.save(new Bus("DL04-RW-5005", "RouteWise Mercedes SuperClass", "Mercedes Multi-Axle AC", 40, "2x2", "Panoramic Windows, WiFi, 140° Reclining, Complimentary Snack Box, CCTV", true));
        Bus b6 = busRepository.save(new Bus("RJ14-RW-6006", "RouteWise Express Executive Seater", "Executive AC Seater", 40, "2x2", "Fast Charger, AC, Overhead Luggage, Live GPS Tracking, CCTV", true));
        Bus b7 = busRepository.save(new Bus("HP01-RW-7007", "RouteWise Himalayan Cruiser Volvo B11R", "Volvo Mountain Spec AC", 36, "2x2", "All-Weather Heating/AC, Mountain Suspension, Panoramic View, First Aid", true));
        Bus b8 = busRepository.save(new Bus("DL09-RW-8008", "RouteWise Golden Triangle Express", "Luxury AC Seater", 40, "2x2", "WiFi, USB Charging, Ergonomic Headrest, Water Bottle", true));
        Bus b9 = busRepository.save(new Bus("PB02-RW-9009", "RouteWise Night Rider Sleeper", "Luxury AC Sleeper", 30, "2x1", "Curtains, Pillow, Soft Blanket, Reading Light, Charging Port", true));
        Bus b10 = busRepository.save(new Bus("UK07-RW-1010", "RouteWise Green Eco Electric Cruiser", "Electric Intercity AC", 36, "2x2", "Zero Emissions, Whisper Quiet Cabin, Type-C Fast Ports, Air Purifier", true));

        // =====================================================================
        // 5. Seed Schedules Across Today, Tomorrow, and Next Days
        // =====================================================================
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LocalDate dayAfter = today.plusDays(2);

        List<Schedule> allSchedules = new ArrayList<>();

        // Delhi -> Chandigarh (Multiple times)
        allSchedules.add(scheduleService.createSchedule(new Schedule(b1, rDelChd, LocalDateTime.of(today, LocalTime.of(6, 30)), LocalDateTime.of(today, LocalTime.of(11, 0)), 650.0, "SCHEDULED")));
        allSchedules.add(scheduleService.createSchedule(new Schedule(b3, rDelChd, LocalDateTime.of(today, LocalTime.of(10, 0)), LocalDateTime.of(today, LocalTime.of(14, 30)), 700.0, "SCHEDULED")));
        allSchedules.add(scheduleService.createSchedule(new Schedule(b5, rDelChd, LocalDateTime.of(today, LocalTime.of(16, 0)), LocalDateTime.of(today, LocalTime.of(20, 30)), 750.0, "SCHEDULED")));
        allSchedules.add(scheduleService.createSchedule(new Schedule(b2, rDelChd, LocalDateTime.of(today, LocalTime.of(22, 30)), LocalDateTime.of(tomorrow, LocalTime.of(3, 0)), 850.0, "SCHEDULED")));
        allSchedules.add(scheduleService.createSchedule(new Schedule(b1, rDelChd, LocalDateTime.of(tomorrow, LocalTime.of(7, 0)), LocalDateTime.of(tomorrow, LocalTime.of(11, 30)), 650.0, "SCHEDULED")));
        allSchedules.add(scheduleService.createSchedule(new Schedule(b5, rDelChd, LocalDateTime.of(tomorrow, LocalTime.of(17, 0)), LocalDateTime.of(tomorrow, LocalTime.of(21, 30)), 750.0, "SCHEDULED")));

        // Chandigarh -> Delhi
        allSchedules.add(scheduleService.createSchedule(new Schedule(b1, rChdDel, LocalDateTime.of(today, LocalTime.of(8, 0)), LocalDateTime.of(today, LocalTime.of(12, 30)), 650.0, "SCHEDULED")));
        allSchedules.add(scheduleService.createSchedule(new Schedule(b3, rChdDel, LocalDateTime.of(today, LocalTime.of(15, 30)), LocalDateTime.of(today, LocalTime.of(20, 0)), 700.0, "SCHEDULED")));
        allSchedules.add(scheduleService.createSchedule(new Schedule(b5, rChdDel, LocalDateTime.of(tomorrow, LocalTime.of(9, 0)), LocalDateTime.of(tomorrow, LocalTime.of(13, 30)), 750.0, "SCHEDULED")));

        // Delhi -> Jaipur
        allSchedules.add(scheduleService.createSchedule(new Schedule(b6, rDelJai, LocalDateTime.of(today, LocalTime.of(7, 30)), LocalDateTime.of(today, LocalTime.of(12, 40)), 550.0, "SCHEDULED")));
        allSchedules.add(scheduleService.createSchedule(new Schedule(b8, rDelJai, LocalDateTime.of(today, LocalTime.of(14, 0)), LocalDateTime.of(today, LocalTime.of(19, 10)), 600.0, "SCHEDULED")));
        allSchedules.add(scheduleService.createSchedule(new Schedule(b2, rDelJai, LocalDateTime.of(tomorrow, LocalTime.of(8, 30)), LocalDateTime.of(tomorrow, LocalTime.of(13, 40)), 650.0, "SCHEDULED")));

        // Chandigarh -> Shimla
        allSchedules.add(scheduleService.createSchedule(new Schedule(b7, rChdShm, LocalDateTime.of(today, LocalTime.of(9, 0)), LocalDateTime.of(today, LocalTime.of(12, 30)), 480.0, "SCHEDULED")));
        allSchedules.add(scheduleService.createSchedule(new Schedule(b7, rChdShm, LocalDateTime.of(tomorrow, LocalTime.of(10, 30)), LocalDateTime.of(tomorrow, LocalTime.of(14, 0)), 480.0, "SCHEDULED")));

        // Chandigarh -> Manali
        allSchedules.add(scheduleService.createSchedule(new Schedule(b7, rChdMnl, LocalDateTime.of(today, LocalTime.of(21, 0)), LocalDateTime.of(tomorrow, LocalTime.of(5, 0)), 950.0, "SCHEDULED")));
        allSchedules.add(scheduleService.createSchedule(new Schedule(b9, rChdMnl, LocalDateTime.of(tomorrow, LocalTime.of(20, 30)), LocalDateTime.of(dayAfter, LocalTime.of(4, 30)), 1050.0, "SCHEDULED")));

        // Chandigarh -> Amritsar
        allSchedules.add(scheduleService.createSchedule(new Schedule(b4, rChdAsr, LocalDateTime.of(today, LocalTime.of(8, 30)), LocalDateTime.of(today, LocalTime.of(12, 50)), 520.0, "SCHEDULED")));
        allSchedules.add(scheduleService.createSchedule(new Schedule(b4, rChdAsr, LocalDateTime.of(tomorrow, LocalTime.of(14, 0)), LocalDateTime.of(tomorrow, LocalTime.of(18, 20)), 520.0, "SCHEDULED")));

        // Delhi -> Dehradun
        allSchedules.add(scheduleService.createSchedule(new Schedule(b10, rDelDed, LocalDateTime.of(today, LocalTime.of(6, 0)), LocalDateTime.of(today, LocalTime.of(11, 30)), 580.0, "SCHEDULED")));
        allSchedules.add(scheduleService.createSchedule(new Schedule(b10, rDelDed, LocalDateTime.of(tomorrow, LocalTime.of(15, 0)), LocalDateTime.of(tomorrow, LocalTime.of(20, 30)), 580.0, "SCHEDULED")));

        // =====================================================================
        // 6. Seed Sample Confirmed Bookings for Real Occupancy & Analytics
        // =====================================================================
        Schedule s1 = allSchedules.get(0);
        BookingRequest req1 = new BookingRequest();
        req1.setScheduleId(s1.getId());
        req1.setSeatNumbers(Arrays.asList("1A", "1B"));
        req1.setPassengerName(passenger.getName());
        req1.setPassengerEmail(passenger.getEmail());
        req1.setPassengerPhone(passenger.getPhone());
        req1.setPassengerAge(28);
        req1.setPassengerGender("Male");
        bookingService.createBooking(req1, passenger);

        Schedule s2 = allSchedules.get(1);
        BookingRequest req2 = new BookingRequest();
        req2.setScheduleId(s2.getId());
        req2.setSeatNumbers(Arrays.asList("2C", "2D"));
        req2.setPassengerName(u2.getName());
        req2.setPassengerEmail(u2.getEmail());
        req2.setPassengerPhone(u2.getPhone());
        bookingService.createBooking(req2, u2);

        Schedule s3 = allSchedules.get(2);
        BookingRequest req3 = new BookingRequest();
        req3.setScheduleId(s3.getId());
        req3.setSeatNumbers(Arrays.asList("3A"));
        req3.setPassengerName(u3.getName());
        req3.setPassengerEmail(u3.getEmail());
        req3.setPassengerPhone(u3.getPhone());
        bookingService.createBooking(req3, u3);

        Schedule s4 = allSchedules.get(6); // Chd -> Del
        BookingRequest req4 = new BookingRequest();
        req4.setScheduleId(s4.getId());
        req4.setSeatNumbers(Arrays.asList("4B", "4C"));
        req4.setPassengerName(u4.getName());
        req4.setPassengerEmail(u4.getEmail());
        req4.setPassengerPhone(u4.getPhone());
        bookingService.createBooking(req4, u4);

        Schedule s5 = allSchedules.get(9); // Del -> Jai
        BookingRequest req5 = new BookingRequest();
        req5.setScheduleId(s5.getId());
        req5.setSeatNumbers(Arrays.asList("1C", "1D"));
        req5.setPassengerName(u5.getName());
        req5.setPassengerEmail(u5.getEmail());
        req5.setPassengerPhone(u5.getPhone());
        bookingService.createBooking(req5, u5);

        // Seed Sample Waiting List Entry
        WaitingListRequest wlReq = new WaitingListRequest();
        wlReq.setScheduleId(s1.getId());
        wlReq.setPassengerName("Sunil Verma");
        wlReq.setPassengerEmail("sunil.verma@example.com");
        wlReq.setContact("+91 99887 76655");
        wlReq.setSeatsRequested(1);
        wlReq.setPriorityLevel(2); // Senior/Medical priority
        waitingListService.addToWaitlist(wlReq, null);

        // =====================================================================
        // 7. Refresh Route Graph
        // =====================================================================
        routeOptimizationService.refreshGraph();
    }
}
