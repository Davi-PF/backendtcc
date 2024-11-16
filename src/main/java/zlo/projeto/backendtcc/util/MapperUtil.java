package zlo.projeto.backendtcc.util;

import org.springframework.stereotype.Component;
import zlo.projeto.backendtcc.repositories.mapper.DozerMapper;

@Component
public class MapperUtil {
    public <T, U> U map(T source, Class<U> targetClass) {
        return DozerMapper.parseObject(source, targetClass);
    }
}

