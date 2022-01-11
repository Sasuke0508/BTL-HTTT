CREATE TABLE Sympton (
  ID             int(10) NOT NULL AUTO_INCREMENT, 
  GroupSymptonID int(10) NOT NULL, 
  Name           varchar(1000) NOT NULL, 
  PRIMARY KEY (ID)) ENGINE=InnoDB;
CREATE TABLE Disease (
  ID          int(10) NOT NULL AUTO_INCREMENT, 
  Name        varchar(255) NOT NULL, 
  Description varchar(1000) NOT NULL, 
  PRIMARY KEY (ID)) ENGINE=InnoDB;
CREATE TABLE DiseaseLevel (
  ID        int(10) NOT NULL AUTO_INCREMENT, 
  DiseaseID int(10) NOT NULL, 
  Name      varchar(255) NOT NULL, 
  PRIMARY KEY (ID)) ENGINE=InnoDB;
CREATE TABLE Reason (
  ID     int(10) NOT NULL AUTO_INCREMENT, 
  Name   varchar(1000) NOT NULL, 
  Weight double NOT NULL, 
  PRIMARY KEY (ID)) ENGINE=InnoDB;
CREATE TABLE Solution (
  ID        int(10) NOT NULL AUTO_INCREMENT, 
  Content   varchar(1000) NOT NULL, 
  Reference varchar(1000), 
  PRIMARY KEY (ID)) ENGINE=InnoDB;
CREATE TABLE `Case` (
  ID         int(10) NOT NULL AUTO_INCREMENT, 
  CreateDate date NOT NULL, 
  PRIMARY KEY (ID)) ENGINE=InnoDB;
CREATE TABLE DiseaseDefingSympton (
  ID     int(10) NOT NULL, 
  Weight double NOT NULL, 
  PRIMARY KEY (ID)) ENGINE=InnoDB;
CREATE TABLE DiseaseLevelDefingSympton (
  ID             int(10) NOT NULL, 
  DiseaseLevelID int(10) NOT NULL, 
  IsQuantitative bit(1) NOT NULL, 
  FromAmount     int(10), 
  ToAmount       int(10), 
  PRIMARY KEY (ID)) ENGINE=InnoDB;
CREATE TABLE GroupSympton (
  ID   int(10) NOT NULL AUTO_INCREMENT, 
  Name varchar(1000) NOT NULL, 
  PRIMARY KEY (ID)) ENGINE=InnoDB;
CREATE TABLE Case_Disease (
  CaseID    int(10) NOT NULL, 
  DiseaseID int(10) NOT NULL, 
  PRIMARY KEY (CaseID, 
  DiseaseID)) ENGINE=InnoDB;
CREATE TABLE DiseaseLevel_Solution (
  DiseaseLevelID int(10) NOT NULL, 
  SolutionID     int(10) NOT NULL, 
  PRIMARY KEY (DiseaseLevelID, 
  SolutionID)) ENGINE=InnoDB;
CREATE TABLE Case_DiseaseLevelDefingSympton (
  CaseID                      int(10) NOT NULL, 
  DiseaseLevelDefingSymptonID int(10) NOT NULL, 
  PRIMARY KEY (CaseID, 
  DiseaseLevelDefingSymptonID)) ENGINE=InnoDB;
CREATE TABLE Case_DiseaseDefingSympton (
  CaseID                 int(10) NOT NULL, 
  DiseaseDefingSymptonID int(10) NOT NULL, 
  PRIMARY KEY (CaseID, 
  DiseaseDefingSymptonID)) ENGINE=InnoDB;
CREATE TABLE Case_Reason (
  CaseID   int(10) NOT NULL, 
  ReasonID int(10) NOT NULL, 
  PRIMARY KEY (CaseID, 
  ReasonID)) ENGINE=InnoDB;
CREATE TABLE Reason_Disease (
  ReasonID  int(10) NOT NULL, 
  DiseaseID int(10) NOT NULL, 
  PRIMARY KEY (ReasonID, 
  DiseaseID)) ENGINE=InnoDB;
CREATE TABLE DiseaseDefingSympton_Disease (
  DiseaseDefingSymptonD int(10) NOT NULL, 
  DiseaseID             int(10) NOT NULL, 
  PRIMARY KEY (DiseaseDefingSymptonD, 
  DiseaseID)) ENGINE=InnoDB;
ALTER TABLE DiseaseDefingSympton ADD CONSTRAINT FKDiseaseDef404172 FOREIGN KEY (ID) REFERENCES Sympton (ID);
ALTER TABLE DiseaseLevelDefingSympton ADD CONSTRAINT FKDiseaseLev307706 FOREIGN KEY (ID) REFERENCES Sympton (ID);
ALTER TABLE Sympton ADD CONSTRAINT FKSympton528449 FOREIGN KEY (GroupSymptonID) REFERENCES GroupSympton (ID);
ALTER TABLE Case_Disease ADD CONSTRAINT FKCase_Disea188151 FOREIGN KEY (CaseID) REFERENCES `Case` (ID);
ALTER TABLE Case_Disease ADD CONSTRAINT FKCase_Disea945529 FOREIGN KEY (DiseaseID) REFERENCES Disease (ID);
ALTER TABLE DiseaseLevel ADD CONSTRAINT FKDiseaseLev426549 FOREIGN KEY (DiseaseID) REFERENCES Disease (ID);
ALTER TABLE DiseaseLevel_Solution ADD CONSTRAINT FKDiseaseLev811550 FOREIGN KEY (DiseaseLevelID) REFERENCES DiseaseLevel (ID);
ALTER TABLE DiseaseLevel_Solution ADD CONSTRAINT FKDiseaseLev253127 FOREIGN KEY (SolutionID) REFERENCES Solution (ID);
ALTER TABLE DiseaseLevelDefingSympton ADD CONSTRAINT FKDiseaseLev815510 FOREIGN KEY (DiseaseLevelID) REFERENCES DiseaseLevel (ID);
ALTER TABLE Case_DiseaseLevelDefingSympton ADD CONSTRAINT FKCase_Disea660428 FOREIGN KEY (CaseID) REFERENCES `Case` (ID);
ALTER TABLE Case_DiseaseLevelDefingSympton ADD CONSTRAINT FKCase_Disea582979 FOREIGN KEY (DiseaseLevelDefingSymptonID) REFERENCES DiseaseLevelDefingSympton (ID);
ALTER TABLE Case_DiseaseDefingSympton ADD CONSTRAINT FKCase_Disea404089 FOREIGN KEY (CaseID) REFERENCES `Case` (ID);
ALTER TABLE Case_DiseaseDefingSympton ADD CONSTRAINT FKCase_Disea394400 FOREIGN KEY (DiseaseDefingSymptonID) REFERENCES DiseaseDefingSympton (ID);
ALTER TABLE Case_Reason ADD CONSTRAINT FKCase_Reaso518027 FOREIGN KEY (CaseID) REFERENCES `Case` (ID);
ALTER TABLE Case_Reason ADD CONSTRAINT FKCase_Reaso236105 FOREIGN KEY (ReasonID) REFERENCES Reason (ID);
ALTER TABLE Reason_Disease ADD CONSTRAINT FKReason_Dis573600 FOREIGN KEY (ReasonID) REFERENCES Reason (ID);
ALTER TABLE Reason_Disease ADD CONSTRAINT FKReason_Dis873446 FOREIGN KEY (DiseaseID) REFERENCES Disease (ID);
ALTER TABLE DiseaseDefingSympton_Disease ADD CONSTRAINT FKDiseaseDef984070 FOREIGN KEY (DiseaseDefingSymptonD) REFERENCES DiseaseDefingSympton (ID);
ALTER TABLE DiseaseDefingSympton_Disease ADD CONSTRAINT FKDiseaseDef591227 FOREIGN KEY (DiseaseID) REFERENCES Disease (ID);
