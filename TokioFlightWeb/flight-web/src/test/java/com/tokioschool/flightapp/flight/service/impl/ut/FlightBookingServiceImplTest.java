package com.tokioschool.flightapp.flight.service.impl.ut;

import com.tokioschool.flightapp.flight.domain.Flight;
import com.tokioschool.flightapp.flight.domain.FlightBooking;
import com.tokioschool.flightapp.flight.dto.FlightBookingDTO;
import com.tokioschool.flightapp.flight.repository.FlightBookingDao;
import com.tokioschool.flightapp.flight.repository.FlightDao;
import com.tokioschool.flightapp.flight.repository.UserDao;
import com.tokioschool.flightapp.flight.service.impl.FlightBookingServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class FlightBookingServiceImplTest {
/*
    @Autowired
    private FlightBookingServiceImpl flightBookingService;

    @Autowired
    private FlightDao flightDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private FlightBookingDao flightBookingDao;

    @Test
    @Order(1)
    void givenABooking_whenNewBookingFlight_thenOk() {

        FlightBookingDTO flightBookingDTO = flightBookingService.newBookingFlight(1L,"0AVRSA787897A");

        assertThat(flightBookingDTO).isNotNull()
                .satisfies(booking-> Objects.nonNull(booking.getLocator()));
    }

    @Test
    @Order(2)
    void givenBookings_whenFindAll_thenList() {

        assertThat(flightBookingService.findAllBooking()).isNotEmpty()
                .hasSizeGreaterThanOrEqualTo(1);
    }
*/
    /**
     * OPTION BEFORE TO JAVA 21
     * givenConcurrentUser_whenBookingLastSlot_thenOnlyOneGuestTheSlot
     *
     * Una de las ejecucines dara una error, al tener @Version diferente
    */
/*
    @Test
    @Order(3)
    void givenBookingsConcurrent_whenNewBookingFlight_thenOk_whitExecutorsService() {
        int occupancyBefore = flightDao.findById(1L).get().getOccupancy();

        String[] userIDs = {"0AVRSA787897A","0AVRSA787897B"};
        // se crea un pool con dos hilos
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // en cada hilo se relaiza una realiza una reserva por usuarios diferntes al mismo vuelo
        IntStream.of(0,1).forEach(thread->{
                executorService.execute(() ->{
                            flightBookingService.newBookingFlight(1L,userIDs[thread]);
                        });
        });

        // se esepra a que se termiente los hilos y como maximo un minuto
        try{
            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        }catch(Exception e){};

        // se recupera el vuelo para ver lo que paso
        Flight currentFLight = flightDao.findById(1L).get();

        // se compruea el numero de reservar para el vuelo
        List<FlightBooking> flightBookings = flightBookingDao.getFlightBookingByFlightId(1L);

        Assertions.assertEquals(occupancyBefore+1,currentFLight.getOccupancy());
    }
*/
    /**
     * OPTION AFTER TO JAVA 21 (virtual threads)
     * givenConcurrentUser_whenBookingLastSlot_thenOnlyOneGuestTheSlot
     *
     * Una de las ejecucines dara una error, al tener @Version diferente
     */
    /*
    @Test
    @Order(4)
    void givenBookingsConcurrent_whenNewBookingFlight_thenOk_whitVritualTrheads() throws InterruptedException {
        String[] userIDs = {"0AVRSA787897A","0AVRSA787897B"};

        // se crea un scope de hilos
        try (StructuredTaskScope<FlightBookingDTO> flightBookingDTOStructuredTaskScope =
                     new StructuredTaskScope()) {
            // se crean las subtareas
            final StructuredTaskScope.Subtask<FlightBookingDTO> subtasks1 =
            flightBookingDTOStructuredTaskScope.fork(() ->
                    flightBookingService.newBookingFlight(1L,userIDs[0]));

            StructuredTaskScope.Subtask<FlightBookingDTO> subtasks2 =
                    flightBookingDTOStructuredTaskScope.fork(() ->
                            flightBookingService.newBookingFlight(1L,userIDs[1]));

            // ejecucion de las tareas y esepra a que termine
            flightBookingDTOStructuredTaskScope.join();

            // se obtiene el sub estado
            StructuredTaskScope.Subtask.State stateSubtask1 = subtasks1.state();
            StructuredTaskScope.Subtask.State stateSubtask2 = subtasks2.state();

            // se compreuba que uno fallo y el otro se ejecuto
            assertThat(List.of(stateSubtask1,stateSubtask2))
                    .containsExactlyInAnyOrder(
                            StructuredTaskScope.Subtask.State.FAILED,
                            StructuredTaskScope.Subtask.State.SUCCESS
                    );

            // error con el servicio serializable

            //if(stateSubtask1 == StructuredTaskScope.Subtask.State.FAILED){
            //    Throwable exception = subtasks1.exception();
            //    assertThat(exception).isInstanceOf(CannotAcquireLockException.class);
            //} else if(stateSubtask2 == StructuredTaskScope.Subtask.State.FAILED){
            //    Throwable exception = subtasks2.exception();
            //    assertThat(exception).isInstanceOf(CannotAcquireLockException.class);
            //}

            // error con version en la entidad
            if(stateSubtask1 == StructuredTaskScope.Subtask.State.FAILED){ // OptimisticLockingFailureException.class
                Throwable exception = subtasks1.exception();
                assertThat(exception).isInstanceOf(CannotAcquireLockException.class);
            } else if(stateSubtask2 == StructuredTaskScope.Subtask.State.FAILED){ // OptimisticLockingFailureException.class
                Throwable exception = subtasks2.exception();
                 assertThat(exception).isInstanceOf(CannotAcquireLockException.class);
            }
        }
    }*/
}