drop database timetableManagementSystem;

create database timetableManagementSystem;
  use timetableManagementSystem;

create table academicYearAndSemester(
	id int not null auto_increment,
	yearName varchar(100)not null ,
	semesterName varchar (100) not null,
	fullName varchar(100) not null,
	constraint primary key(id)
);

create table programme(
	programmeid int not null auto_increment,
	programmeName varchar(100)not null ,
	constraint primary key(programmeid)
);

create table tag(
	tagid int not null auto_increment,
	tagName varchar(100)not null ,
	constraint primary key(tagid)
);

create table maingroup(
  id int not null auto_increment,
  mgroupName varchar (100) not null,
  groupNumber int,
  groupid varchar(100) not null,
  programmeid int,
  semid int,
  constraint primary key(id),
  FOREIGN KEY (programmeid) REFERENCES programme(programmeid) ON DELETE CASCADE,
  FOREIGN KEY (semid) REFERENCES academicYearAndSemester(id) ON DELETE CASCADE
);

create table subgroup(
  id int not null auto_increment,
  subgroupid varchar(100) not null,
  subgroupnumber int ,
  maingroupid int,
  constraint primary key(id),
  FOREIGN KEY (maingroupid) REFERENCES maingroup(id)ON DELETE CASCADE
);

create table building(
	bid int not null auto_increment,
	center varchar(100) not null,
	building varchar(100) not null,
	constraint primary key(bid)
);

create table room(
	rid int not null auto_increment,
	buildingid int not null,
	room varchar(100) not null,
	capacity int not null,
	constraint primary key(rid),
	FOREIGN KEY (buildingid) REFERENCES building(bid) ON DELETE CASCADE
);



create table department(
  dId int not null auto_increment,
  departmentName  varchar (30) not null,
  constraint primary key(dId)
);

CREATE TABLE Lecturer (
  employeeId INT(6) UNSIGNED PRIMARY KEY,
  employeeName VARCHAR(30) NOT NULL,
  faculty VARCHAR(30),
  departmentId int,
  designation VARCHAR(100) NOT NULL,
  center VARCHAR(50),
  buildingId int ,
  level INT(1) NOT NULL ,
  ranks VARCHAR(8) NOT NULL,
  FOREIGN KEY (buildingId) REFERENCES building(bid) ON DELETE CASCADE,
  FOREIGN KEY (departmentId) REFERENCES department(dId) ON DELETE CASCADE
);
CREATE TABLE Subject (
  subId VARCHAR(10)  PRIMARY KEY,
  subName VARCHAR(30) NOT NULL,
  offeredYearSemId INT NOT NULL,
  noLecHrs INT NOT NULL,
  noTutHrs INT NOT NULL,
  noEvalHrs INT NOT NULL,
  FOREIGN KEY (offeredYearSemId) REFERENCES academicYearAndSemester(id) ON DELETE CASCADE
);

create table WorkingDaysMain(
  workingId  int not null auto_increment,
  type varchar (30) NOT NULL,
  noOfDays int NOT null,
  constraint primary key(workingId)
);

create table WorkingDaysSub(
  subId int not null auto_increment,
  workingId int not null,
  workingday varchar (30) NOT NULL ,
  FOREIGN KEY (workingId) REFERENCES WorkingDaysMain(workingId) ON DELETE CASCADE,
  constraint primary key(subId)
);

create table workingHoursPerDay(
     whpId int not null auto_increment,
     workingTime varchar(20),
     timeSlot varchar (20),
     constraint primary key (whpId)
);

create table notAvailableGroup(
    id int not null auto_increment,
    day varchar (20),
    toTime TIME,
    fromTime TIME,
    groupId varchar (100),
    subgroupId int,
    mainGroupId int,
    FOREIGN KEY (subgroupId) REFERENCES subgroup(id) ON DELETE CASCADE,
    FOREIGN KEY (mainGroupId) REFERENCES maingroup(id) ON DELETE CASCADE,
    constraint primary key (id)
);

create table notAvailableLecture(
    id int not null auto_increment,
    day varchar (20),
    toTime TIME,
    fromTime TIME,
    lectureId  INT(6) UNSIGNED,
    FOREIGN KEY (lectureId) REFERENCES Lecturer(employeeId) ON DELETE CASCADE,
    constraint primary key (id)
);



CREATE TABLE Session(
    sessionId int PRIMARY KEY auto_increment,
    subjectId varchar(10),
    tagId int,
    groupId int  NULL,
    subGroupId int  NULL,
    studentCount int,
    duration float,
    isConsecutive varchar(5),
    consectiveAdded varchar(5),
    Constraint fk_key2 FOREIGN KEY(subjectId) REFERENCES Subject(subId) ON DELETE CASCADE,
    Constraint fk_key3 FOREIGN KEY(tagId) REFERENCES tag(tagid) ON DELETE CASCADE,
    Constraint fk_key4 FOREIGN KEY(groupId) REFERENCES maingroup(id) ON DELETE CASCADE,
    Constraint fk_key5 FOREIGN KEY(subGroupId) REFERENCES subgroup(id) ON DELETE CASCADE
);

CREATE TABLE SessionLecture(
    id  int PRIMARY KEY auto_increment,
    lecturerId INT(6) UNSIGNED,
    sessionId int,
    Constraint fk_keySessionLectureLectId FOREIGN KEY(lecturerId) REFERENCES Lecturer(employeeId) ON DELETE CASCADE,
    Constraint fk_keySessionLecturesessionId FOREIGN KEY(sessionId) REFERENCES Session(sessionId) ON DELETE CASCADE
)

CREATE TABLE ConsectiveSession(
  id int PRIMARY KEY auto_increment,
  sessionId int,
  consectiveId int,
  Constraint fk_keyConsectiveSessionId FOREIGN KEY(sessionId) REFERENCES Session(sessionId) ON DELETE CASCADE,
  Constraint fk_keyconsectiveId FOREIGN KEY(consectiveId) REFERENCES Session(sessionId) ON DELETE CASCADE
);


CREATE TABLE NotAvailableSession(
  id int PRIMARY KEY auto_increment,
  sessionId int,
  day varchar(10),
  toTime TIME,
  fromTime TIME,
  FOREIGN KEY (sessionId) REFERENCES Session(sessionId) ON DELETE CASCADE
);





CREATE TABLE PrefRoomTag(
	id int PRIMARY KEY auto_increment,
	tagId int,
	roomId int,
	Constraint fk_tagId FOREIGN KEY(tagId) REFERENCES tag(tagid),
	Constraint fk_roomId_tag FOREIGN KEY(roomId) REFERENCES room(rid)
);

CREATE TABLE PrefRoomSubject(
	id int PRIMARY KEY auto_increment,
	tagId int,
	subjectId VARCHAR(10),
	roomId int,
	Constraint fk_subjectId_pref FOREIGN KEY(subjectId) REFERENCES Subject(subId),
	Constraint fk_tagId_pref FOREIGN KEY(tagId) REFERENCES tag(tagid),
	Constraint fk_roomId_subject FOREIGN KEY(roomId) REFERENCES room(rid)
);

CREATE TABLE PrefRoomLecturer(
	id int PRIMARY KEY auto_increment,
	employeeId INT(6) UNSIGNED ,
	roomId int,
	Constraint fk_employeeId FOREIGN KEY(employeeId) REFERENCES Lecturer(employeeId),
	Constraint fk_roomId_lecturer FOREIGN KEY(roomId) REFERENCES room(rid)
);

CREATE TABLE PrefRoomGroup(
	id int PRIMARY KEY auto_increment,
	groupId int NULL,
	subGroupId int NULL,
	roomId int,
	Constraint fk_groupId FOREIGN KEY(groupId) REFERENCES maingroup(id),
	Constraint fk_subGroupId FOREIGN KEY(subGroupId) REFERENCES subgroup(id),
	Constraint fk_roomId_group FOREIGN KEY(roomId) REFERENCES room(rid)
);

CREATE TABLE PrefRoomSession(
	id int PRIMARY KEY auto_increment,
	sessionId int,
	roomId int,
	Constraint fk_sessionId FOREIGN KEY(sessionId) REFERENCES Session(sessionId),
	Constraint fk_roomId_session FOREIGN KEY(roomId) REFERENCES room(rid)
);

CREATE TABLE PrefRoomReserved(
	id int PRIMARY KEY auto_increment,
	roomId int,
	day varchar(10),
	toTime TIME,
	fromTime TIME,
	Constraint fk_roomId_reserved FOREIGN KEY(roomId) REFERENCES room(rid)
);
ALTER TABLE Subject
ADD  subjectType varchar(20);

ALTER TABLE Subject
ADD  category varchar(20);

create TABLE timetable(
  id int PRIMARY KEY auto_increment,
  sessionId int,
  day varchar(20),
  roomId int,
  toTime TIME,
  fromTime TIME,
  timeString varchar(20),
  Constraint fk_sessionId_time_table FOREIGN KEY(sessionId) REFERENCES Session(sessionId),
  Constraint fk_roomId_time_table FOREIGN KEY(roomId) REFERENCES room(rid)
)

ALTER TABLE Session
ADD  isParallel varchar(20);

ALTER TABLE Session
ADD  category varchar(5);


ALTER TABLE Subject
ADD  subjectType varchar(20);

ALTER TABLE Subject
ADD  category varchar(20);

create Table parrellSessions(
      id int PRIMARY KEY auto_increment,
      sessionId int,
      orderId varchar(20),
      Constraint fk_Prallel_sessionId_time_table FOREIGN KEY(sessionId) REFERENCES Session(sessionId)
)

create Table savegrouptimetable(
      id int PRIMARY key auto_increment,
      groupId varchar (30),
      file varchar(300)
)