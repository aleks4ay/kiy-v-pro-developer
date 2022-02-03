package org.aleks4ay.developer.model;

import javafx.scene.control.CheckBox;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public enum StatusName implements Comparable<StatusName>{
    NEW {
        @Override
        public String toStringRus() {
            return "Новый";
        }
    },
    PARSED {
        @Override
        public String toStringRus() {
            return "Поступил на распределение";
        }
    },
    KB_NEW {
        @Override
        public String toStringRus() {
            return "Поступил в КБ";
        }
    },
    KB_START {
        @Override
        public String toStringRus() {
            return "Начало разработки";
        }
    },
    KB_QUESTION {
        @Override
        public String toStringRus() {
            return "Согласование";
        }
    },
    KB_CONTINUED {
        @Override
        public String toStringRus() {
            return "Получен ответ";
        }
    },
    KB_END {
        @Override
        public String toStringRus() {
            return "Конец разработки";
        }
    },
    FACTORY {
        @Override
        public String toStringRus() {
            return "Запуск в цехе";
        }
    },
    FACTORY_DONE {
        @Override
        public String toStringRus() {
            return "Изготовлен";
        }
    },
    SHIPMENT {
        @Override
        public String toStringRus() {
            return "Готов к отгрузке";
        }
    },
    SHIPMENT_REAL {
        @Override
        public String toStringRus() {
            return "Готов к отгрузке";
        }
    },
    COMPLETE {
        @Override
        public String toStringRus() {
            return "Отгружен";
        }
    },
    NOT_TRACKED {
        @Override
        public String toStringRus() {
            return "Не отслеживается";
        }
        @Override
        public String toStringRusToCell() {
            return "Не отсле-" + System.lineSeparator() + "живается";
        }
    },
    CANCELED {
        @Override
        public String toStringRus() {
            return "Отменен";
        }
    },
    UNKNOWN {
        @Override
        public String toStringRus() {
            return "Не опознан";
        }
    };

    public String toStringRus() {
        return "";
    }

    public String toStringRusToCell() {
        return toStringRus().replaceFirst(" ", System.lineSeparator());
    }

    public static Set<StatusName> addStatusFromCheckBox(CheckBox checkBox) {
        Set<StatusName> result = new HashSet<>();
        switch (checkBox.getId()) {
            case "check_all": {
                result.addAll(Arrays.asList(values()));
                break;
            }
            case "check_kb": {
                result.addAll(Arrays.asList(KB_NEW, KB_START, KB_QUESTION, KB_CONTINUED));
                break;
            }
            case "check_ceh": {
                result.add(FACTORY);
                break;
            }
            case "check_done": {
                result.add(FACTORY_DONE);
                break;
            }
            case "check_shipm": {
                result.add(COMPLETE);
                break;
            }
            case "check_other": {
                result.addAll(Arrays.asList(NOT_TRACKED, CANCELED, UNKNOWN));
                break;
            }
            default: {
                result.add(NEW);
            }
        }
        return result;
    }
}