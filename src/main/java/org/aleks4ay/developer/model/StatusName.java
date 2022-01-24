package org.aleks4ay.developer.model;

public enum StatusName implements Comparable<StatusName>{
    NEW(0) {
        @Override
        public String toStringRus() {
            return "Новый";
        }
    },
    PARSED(1) {
        @Override
        public String toStringRus() {
            return "Поступил на распределение";
        }
    },
    KB_NEW(2) {
        @Override
        public String toStringRus() {
            return "Поступил в КБ";
        }
    },
    KB_START(3) {
        @Override
        public String toStringRus() {
            return "Начало разработки";
        }
    },
    KB_QUESTION(4) {
        @Override
        public String toStringRus() {
            return "Согласование";
        }
    },
    KB_CONTINUED(5) {
        @Override
        public String toStringRus() {
            return "Получен ответ";
        }
    },
    KB_END(6) {
        @Override
        public String toStringRus() {
            return "Конец разработки";
        }
    },
    FACTORY(7) {
        @Override
        public String toStringRus() {
            return "Запуск в цехе";
        }
    },
    FACTORY_DONE(18) {
        @Override
        public String toStringRus() {
            return "Изготовлен";
        }
    },
    SHIPMENT(19) {
        @Override
        public String toStringRus() {
            return "Готов к отгрузке";
        }
    },
    COMPLETE(20) {
        @Override
        public String toStringRus() {
            return "Отгружен";
        }
    },
    NOT_TRACKED(24) {
        @Override
        public String toStringRus() {
            return "Не отслеживается";
        }
        @Override
        public String toStringRusToCell() {
            return "Не отсле-" + System.lineSeparator() + "живается";
        }
    },
    CANCELED(30) {
        @Override
        public String toStringRus() {
            return "Отменен";
        }
    },
    UNKNOWN(66) {
        @Override
        public String toStringRus() {
            return "Не опознан";
        }
    };

    private int statusIndex;

    StatusName(int statusIndex) {
        this.statusIndex = statusIndex;
    }

    public int getStatusIndex() {
        return statusIndex;
    }

    public String toStringRus() {
        return "";
    }

    public String toStringRusToCell() {
        return toStringRus().replaceFirst(" ", System.lineSeparator());
    }
}
