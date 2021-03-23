package service.stat.dto;

import lombok.Getter;

/**
 * Класс критериев для поиска стастики за период содежит:
 * 1. startDate - дата начала поиска
 * 2. endDate - дата окончания поиска
 */
public class CriteriaStat {
    @Getter
    private String startDate;
    @Getter
    private String endDate;
}
