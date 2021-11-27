package com.iot.server.dao;

import com.iot.server.common.dto.BaseDto;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DaoUtil {

  private DaoUtil() {
  }

  public static <T extends BaseDto> T getDto(ToDto<T> entity) {
    if (entity != null) {
      return entity.toDto();
    }
    return null;
  }

  public static <T extends BaseDto> T getDto(Optional<? extends ToDto<T>> entity) {
    return entity.map(ToDto::toDto).orElse(null);
  }

  public static <T extends BaseDto> List<T> getDtos(Collection<? extends ToDto<T>> entities) {
    if (entities != null && !entities.isEmpty()) {
      return entities.stream()
          .map(ToDto::toDto)
          .collect(Collectors.toList());
    }
    return Collections.emptyList();
  }
}
