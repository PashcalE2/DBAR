create type статус_заказа_enum as enum (
    'формируется',
    'ожидает оплаты',
    'выполняется',
    'выполнен',
    'отклонен'
);

create type статус_продукции_в_заказе_enum as enum (
    'ожидает производства',
    'ожидает сборки',
    'ожидает возвращения',
    'возвращена',
    'собрана под заказ'
);

create type отправитель_enum as enum (
    'клиент',
	'консультант'
);

create type должность_enum as enum (
    'механик',
    'электрик',
    'технолог',
    'руководитель'
);

create type состояние_оборудования_enum as enum (
    'исправно',
	'неисправно'
);

create type форма_сотрудничества_enum as enum (
    'благотворительность',
    'партнерство'
);

create type состояние_сотрудничества_enum as enum (
    'на рассмотрении',
    'в силе',
    'прекращено',
    'отклонено'
);

-- Более клиент-ориентированная часть

create table служба_поддержки (
    ид serial primary key,
    название text not null,
    номер_телефона varchar(20) not null,
	email varchar(64) not null,
    адрес text unique not null
);

create table расписание_консультантов (
    ид serial primary key,
	рабочее_время int not null check (рабочее_время >= 0),
	описание text
);

create table консультант (
    ид serial primary key,
	ид_службы_поддержки int not null references служба_поддержки(ид),
	ид_расписания int not null references расписание_консультантов(ид),
	ФИО varchar(64) not null,
    номер_телефона varchar(20) not null,
    email varchar(64) not null,
    пароль varchar(64) not null
);

create table клиент (
    ид serial primary key,
	номер_телефона varchar(20) not null,
    email varchar(64) not null,
    пароль varchar(64) not null,
    название varchar(64) unique not null
);

create table заказ (
    ид serial primary key,
	ид_клиента int not null references клиент(ид),
	ид_консультанта int not null references консультант(ид),
	статус статус_заказа_enum not null,
	поступил timestamp not null,
	завершен timestamp
);

create table сообщение (
    ид serial primary key,
	ид_заказа int not null references заказ(ид),
	отправитель отправитель_enum not null,
	текст text not null,
	дата_время timestamp not null
);

create table тип_продукции (
    ид serial primary key,
	цена real not null check (цена > 0),
    название text,
    описание text
);

create table продукция_в_заказе (
	ид_заказа int not null references заказ(ид),
	ид_типа int not null references тип_продукции(ид),
	primary key (ид_заказа, ид_типа),
	статус статус_продукции_в_заказе_enum not null,
	количество int not null check (количество > 0)
);

-- Более внутренняя часть

create table завод (
    ид serial primary key,
	название text not null,
	номер_телефона varchar(20) not null,
	email varchar(64) not null,
	пароль varchar(64) not null,
    адрес text unique not null
);

create table склад_готовой_продукции (
    ид serial primary key,
	ид_завода int references завод(ид),
    адрес text unique not null
);

create table готовая_продукция (
	ид_склада int references склад_готовой_продукции(ид),
	ид_типа int references тип_продукции(ид),
	primary key (ид_склада, ид_типа),
	количество int not null check (количество >= 0)
);

create table склад_сырья (
    ид serial primary key,
	ид_завода int references завод(ид),
    адрес text unique not null
);

create table тип_материала (
	ид serial primary key,
	цена real not null check (цена > 0),
    название text,
    описание text
);

create table материалы (
	ид_склада int references склад_сырья(ид),
	ид_типа int references тип_материала(ид),
	primary key (ид_склада, ид_типа),
	количество int not null check (количество >= 0)
);

create table организация (
    ид serial primary key,
	номер_телефона varchar(20) not null,
	email varchar(64) not null,
    название varchar(64) unique not null
);

create table сотрудничество (
    ид_завода int references завод(ид),
    ид_организации int references организация(ид),
	primary key (ид_завода, ид_организации),
	форма форма_сотрудничества_enum not null,
	состояние состояние_сотрудничества_enum not null,
	поступило timestamp not null,
	завершено timestamp
);

create table цех (
    ид serial primary key,
	ид_завода int not null references завод(ид),
	название text not null
);

create table расписание_сотрудников (
    ид serial primary key,
	рабочее_время int not null check (рабочее_время >= 0),
	описание text
);

create table сотрудник (
    ид serial primary key,
	ид_цеха int not null references цех(ид),
	ид_расписания int not null references расписание_сотрудников(ид),
	ФИО varchar(64) not null,
    должность должность_enum not null,
    номер_телефона varchar(20) not null,
    email varchar(64) not null,
    пароль varchar(64) not null
);

create table оборудование (
    ид serial primary key,
	ид_цеха int not null references цех(ид),
	состояние состояние_оборудования_enum not null,
	название text not null,
	описание text,
    дата_выпуска date not null
);


