package org.example.factory_core_manager.service.exceptionHandler;


import org.example.factory_core_manager.exception.*;
import org.example.factory_core_manager.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandler {


    @org.springframework.web.bind.annotation.ExceptionHandler(WorkerNotFoundByIdException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse responseWorkerNotFoundById (WorkerNotFoundByIdException workerNotFoundById) {
       return new ExceptionResponse(workerNotFoundById.getMessage() , LocalDateTime.now());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(WorkerCodeIsDuplicatedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse responseWorkerCodeDuplicate (WorkerCodeIsDuplicatedException workerCodeIsDuplicated) {
        return new ExceptionResponse(workerCodeIsDuplicated.getMessage() , LocalDateTime.now());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ProfileNotFoundByWorkerCodeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProfileNotFoundByWorkerCodeException profileNotFoundByWorkerCode(ProfileNotFoundByWorkerCodeException profileNotFoundByWorkerCode) {
        return new ProfileNotFoundByWorkerCodeException(profileNotFoundByWorkerCode.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MultipleProfileByWorkerCodeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MultipleProfileByWorkerCodeException multipleProfileByWorkerCode(MultipleProfileByWorkerCodeException multipleProfileByWorkerCode) {
        return new MultipleProfileByWorkerCodeException(multipleProfileByWorkerCode.getMessage());
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(WorkerAlreadyCheckedInException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse workerAlreadyCheckedIn(WorkerAlreadyCheckedInException workerAlreadyCheckedIn) {
        return new ExceptionResponse(workerAlreadyCheckedIn.getMessage() , LocalDateTime.now());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(WorkerNotStartedToWorkException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse workerNotStartedToWork(WorkerNotStartedToWorkException workerNotStartedToWork) {
        return new ExceptionResponse(workerNotStartedToWork.getMessage() , LocalDateTime.now());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DuplicateRequestForCheckOutException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse duplicateRequestForCheckIn(DuplicateRequestForCheckOutException duplicateRequestForCheckOut) {
        return new ExceptionResponse(duplicateRequestForCheckOut.getMessage() , LocalDateTime.now());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(VacationInTimeIsAlreadyUsed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse vacationInTimeIsAlreadyUsed(VacationInTimeIsAlreadyUsed vacationInTimeIsAlreadyUsed) {
        return new ExceptionResponse(vacationInTimeIsAlreadyUsed.getMessage() , LocalDateTime.now());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(LogicalRuntimeException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse logicalRuntimeException(LogicalRuntimeException logicalRuntimeException) {
        return new ExceptionResponse(logicalRuntimeException.getMessage() , LocalDateTime.now());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(WorkerNotGetVacationInTime.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse workerNotGetVacationInTime (WorkerNotGetVacationInTime workerNotGetVacationInTime) {
        return new ExceptionResponse(workerNotGetVacationInTime.getMessage() , LocalDateTime.now());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(VacationOutTimeAlreadyUsed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse vacationOutTimeAlreadyUsed  (VacationOutTimeAlreadyUsed vacationOutTimeAlreadyUsed) {
        return new ExceptionResponse(vacationOutTimeAlreadyUsed.getMessage() , LocalDateTime.now());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(WorkerHasCheckedOutException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse workerHasCheckedOutException(WorkerHasCheckedOutException workerHasCheckedOutException) {
        return new ExceptionResponse(workerHasCheckedOutException.getMessage() , LocalDateTime.now());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(WorkerNotCorrectlyStartedFinishedVacation.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse WorkerNotCorrectlyStartedFinishedVacation(WorkerNotCorrectlyStartedFinishedVacation workerNotCorrectlyStartedFinishedVacation) {
        return new ExceptionResponse(workerNotCorrectlyStartedFinishedVacation.getMessage() , LocalDateTime.now());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MultipleTransActionForSpecificDate.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse MultipleTransActionForSpecificDate  (MultipleTransActionForSpecificDate multipleTransActionForSpecificDate) {
        return new ExceptionResponse(multipleTransActionForSpecificDate.getMessage() , LocalDateTime.now());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(PaymentDegreeNotExistException.class)
    public ExceptionResponse paymentDegreeNotExist(PaymentDegreeNotExistException paymentDegreeNotExistException) {
        return new ExceptionResponse(paymentDegreeNotExistException.getMessage() , LocalDateTime.now());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DuplicateProductNameException.class )
    public ExceptionResponse duplicateProductName(DuplicateProductNameException duplicateProductNameException) {
        return new ExceptionResponse(duplicateProductNameException.getMessage() , LocalDateTime.now());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DuplicateInventoryNameException.class)
    public ExceptionResponse duplicateInventoryName(DuplicateInventoryNameException duplicateInventoryNameException) {
        return new ExceptionResponse(duplicateInventoryNameException.getMessage() , LocalDateTime.now());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(InventoryNotPersistedException.class)
    public ExceptionResponse inventoryNotPersisted(InventoryNotPersistedException inventoryNotPersistedException) {
        return new ExceptionResponse(inventoryNotPersistedException.getMessage() , LocalDateTime.now());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ProductNotExistException.class)
    public ExceptionResponse productNotExist(ProductNotExistException productNotExistException) {
        return new ExceptionResponse(productNotExistException.getMessage() , LocalDateTime.now());
    }


}
