package com.madhatters.wazan.repositories;

import com.madhatters.wazan.model.Form;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FormRepository extends ResourceRepository<Form,String>{
    Page<Form> findAllByTimeStampGreaterThanOrTimeStampIs(Pageable pageable,
                                                          long timeStamp, long time);
}
