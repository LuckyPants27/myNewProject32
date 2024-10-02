package pro.sky.mynewproject32.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pro.sky.mynewproject32.exception.IncorrectNameException;

@Service
public class ValidationService {

    public String validate(String name) {
        if (!StringUtils.isAlpha(name)) {
            throw new IncorrectNameException();
        }
        return StringUtils.capitalize(name.toLowerCase());
    }
}
