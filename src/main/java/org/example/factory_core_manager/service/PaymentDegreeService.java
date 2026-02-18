package org.example.factory_core_manager.service;

import org.example.factory_core_manager.convertor.Convertor;
import org.example.factory_core_manager.dto.PaymentDegreeMenu;
import org.example.factory_core_manager.dto.PaymentDegreeSave;
import org.example.factory_core_manager.entity.PaymentDegree;
import org.example.factory_core_manager.exception.PaymentDegreeNotExistException;
import org.example.factory_core_manager.repository.PaymentDegreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class PaymentDegreeService {

    private  PaymentDegreeRepository paymentDegreeRepository;

    private Convertor convertor;


    @Autowired
    public PaymentDegreeService(PaymentDegreeRepository paymentDegreeRepository , Convertor convertor) {
        this.paymentDegreeRepository = paymentDegreeRepository;
        this.convertor = convertor;
    }

    public PaymentDegreeMenu getPaymentDegreeMenu() {
        return convertor.paymentDegreeToPaymentDegreeMenu(this.getOrCreatePaymentDegree());
    }

    public PaymentDegree getThePaymentDegree() {
        return this.getOrCreatePaymentDegree();
    }

    private PaymentDegree getOrCreatePaymentDegree() {
        Optional<PaymentDegree> paymentDegree = paymentDegreeRepository.findById(1L);
        if (paymentDegree.isPresent()) {
            return paymentDegree.get();
        }
        PaymentDegree newPaymentDegree = new PaymentDegree();
        return this.paymentDegreeRepository.save(newPaymentDegree);

    }

    public void editPaymentDegree(PaymentDegreeSave paymentDegreeSave) {

        PaymentDegree paymentDegree = this.getOrCreatePaymentDegree();
        PaymentDegree paymentDegree1= convertor.paymentDegreeSaveToPaymentDegree(paymentDegreeSave);
        if(paymentDegree.getMarriedPayment()!=paymentDegree1.getMarriedPayment()) {
            paymentDegree.setMarriedPayment(paymentDegree1.getMarriedPayment());
        }
        if (paymentDegree.getHavingChildrenPayment()!=paymentDegree1.getHavingChildrenPayment()) {
            paymentDegree.setHavingChildrenPayment(paymentDegree1.getHavingChildrenPayment());
        }
        if (paymentDegree.getHavingAChildPayment()!=paymentDegree1.getHavingAChildPayment()) {
            paymentDegree.setHavingAChildPayment(paymentDegree1.getHavingAChildPayment());
        }
        if (paymentDegree.getBackGroundYearsPaymentPerYear()!=paymentDegree1.getBackGroundYearsPaymentPerYear()) {
            paymentDegree.setBackGroundYearsPaymentPerYear(paymentDegree1.getBackGroundYearsPaymentPerYear());
        }
        this.paymentDegreeRepository.save(paymentDegree);


    }

}
