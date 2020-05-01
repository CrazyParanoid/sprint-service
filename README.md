# SPRINT-SERVICE

Микросервис управления спринтами приложения SCRUM Project Management Software. При разработке использовались тактические и стратегические шаблоны DDD, паттерны GoF, принципы чистого кода, SOLID и гексагональной архитектуры. Ограниченный контекст микросервиса - модель спринта в методологии гибкой разработки SCRUM.

# Описание агрегата Sprint
Основной элемент модели ограниченного контекста микросервиса - агрегат Sprint. Корень агрегата инкапсулирует набор объектов-значений: 
  - sprintId - уникальный идентификатор спринта. Создаётся статическим фабричным методом identifySprint класса SprintId;
  - period - период спринта. Содержит начальную и конечную даты спринта. Создаётся статическим фабричным методом between класса SprintPeriod;
  - name - наименование спринта;
  - goal - цель спринта;
  - project - проект, которому принадлежит спринт;
  - status - статус спринта. Возможные значения: INACTIVE - новый спринт, который еще не был спланирован и взят в работу; ACTIVE - активный спринт, который был спланирован; COMPLETE - завершенный спринт;
  - tasks - множество задач, запланированных в спринте, содержит идентификаторы задач TaskId. Создаётся статическим фабричным методом identifyTaskFrom класса TaskId;

Создание нового спринта осуществляется в статическом фабричном методе create класса Sprint. Метод create принимает в качестве параметров name - наименование спринта, goal - цель спринта, projectKey - ключ проекта.

Для планирования спринта необходимо мспользовать функцию schedule. В качестве аргумента функция принимает идентификатор задачи taskId. Функция контролирует соблюдение инварианта предметной области - только новый или активный спринт могут быть спланированы. Функция возвращает событие предметной области SprintScheduled, которое содержит идентификаторы спринта и задачи. Подписчиком данного события является агрегат Task.

Для запуска спринта необходимо использовать функцию start, принимающую параметры периода спринта - начальную и конечную даты. Функция контролирует инварианты предметной области - только новый спринт можно запустить, у спринта должны быть запланированные задачи (функция checkReadyForStart). В теле функции start вычисляется период спринта SprintPeriod.

Для завершения спринта необходимо использовать функцию complete. Функция контролирует инвариант - только активный спринт можно завершить.

Корень агрегата sprint содержит ряд методов без побочных эффектов (например, endDateOfSprint, daysOfSprint, status и т.д.), раскрывающих внутреннюю структуру агрегата для сборщика DTO (SprintAssembler). Структура раскрывается ровно в той степени, в которой того требует уровень представления presentation. Практически все геттеры и сеттеры корня и объектов значений самоинкапсулированы и не позволяют получить доступ к внутренней структуре агрегата.
 
# Хранилище SprintRepository
Хранилище объектов sprint выполнено в виде выделенного интерфейса SprintRepository на уровне предметной области (пакет domain). В соответсвии с принципами гексагональной архитектуры интерфейс хранилища является вторичным портом, адаптер к которому находится на уровне инфраструктуры (пакет infrastructure). Внедрение зависимости осуществляется по принципу DIP.
# Служба предментой области DomainEventPublisher
Для публикации событий предметной области используется служба предметной области DomainEventPublisher. Реализация службы находится на уровне инфраструктуры. Агрегат sprint является источником события SprintScheduled, возникающего после планирования спринта. Данное событие публикуется службой DomainEventPublisher с использованием MoM Kafka. Событие SprintScheduled используется для интеграции ограниченных контекстов спринта Sprint и задачи Task.






