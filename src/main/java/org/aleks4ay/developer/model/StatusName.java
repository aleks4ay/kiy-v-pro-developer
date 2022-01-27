package org.aleks4ay.developer.model;

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
}
