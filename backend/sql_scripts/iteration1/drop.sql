---Удаление таблицы базы данных

drop type if exists доступные_организации_enum cascade;

drop type if exists статус_заказа_enum cascade;

drop type if exists статус_продукции_в_заказе_enum cascade;

drop type if exists отправитель_enum cascade;

drop type if exists должность_enum cascade;

drop type if exists состояние_оборудования_enum cascade;

drop type if exists форма_сотрудничества_enum cascade;

drop type if exists состояние_сотрудничества_enum cascade;

drop table if exists служба_поддержки cascade;

drop table if exists расписание_консультантов cascade;

drop table if exists консультант cascade;

drop table if exists клиент cascade;

drop table if exists заказ cascade;

drop table if exists сообщение cascade;

drop table if exists тип_продукции cascade;

drop table if exists продукция_в_заказе cascade;

drop table if exists завод cascade;

drop table if exists склад_готовой_продукции cascade;

drop table if exists готовая_продукция cascade;

drop table if exists склад_сырья cascade;

drop table if exists материалы cascade;

drop table if exists тип_материала cascade;

drop table if exists организация cascade;

drop table if exists сотрудничество cascade;

drop table if exists цех cascade;

drop table if exists расписание_сотрудников cascade;

drop table if exists сотрудник cascade;

drop table if exists оборудование cascade;
