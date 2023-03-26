package martinrahe.fujitsu_internship_test_deliveryprice;

import martinrahe.fujitsu_internship_test_deliveryprice.errors.ForbiddenVehicleTypeException;
import martinrahe.fujitsu_internship_test_deliveryprice.errors.RBFNotFoundException;
import martinrahe.fujitsu_internship_test_deliveryprice.errors.VehicleTypeNotFoundException;
import martinrahe.fujitsu_internship_test_deliveryprice.errors.WeatherDataNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(WeatherDataNotFoundException.class)
    private ResponseEntity<Object> handleWeatherDataNotFoundException(WeatherDataNotFoundException exc, WebRequest req) {
        String msg = "No weather data for this station recorded before the requested time was found";
        return handleExceptionInternal(exc, msg, new HttpHeaders(), HttpStatus.NOT_FOUND, req);
    }

    @ExceptionHandler(VehicleTypeNotFoundException.class)
    private ResponseEntity<Object> handleVehicleTypeNotFoundException(VehicleTypeNotFoundException exc, WebRequest req) {
        String msg = "The requested vehicle is not recognised";
        return handleExceptionInternal(exc, msg, new HttpHeaders(), HttpStatus.NOT_FOUND, req);
    }

    @ExceptionHandler(RBFNotFoundException.class)
    private ResponseEntity<Object> handleRBFNotFoundException(RBFNotFoundException exc, WebRequest req) {
        String msg = "Missing regional base fee for this location";
        return handleExceptionInternal(exc, msg, new HttpHeaders(), HttpStatus.NOT_FOUND, req);
    }

    @ExceptionHandler(ForbiddenVehicleTypeException.class)
    private ResponseEntity<Object> handleForbiddenVehicleTypeException(ForbiddenVehicleTypeException exc, WebRequest req) {
        String msg = "Usage of selected vehicle type is forbidden";
        return handleExceptionInternal(exc, msg, new HttpHeaders(), HttpStatus.NOT_FOUND, req);
    }
}
