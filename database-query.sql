
DROP DATABASE IF EXISTS campinglounge;
CREATE DATABASE campinglounge;
USE campinglounge;



CREATE TABLE Member (
    member_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_email VARCHAR(255) NOT NULL UNIQUE,
    member_name VARCHAR(100) NOT NULL,
    member_password VARCHAR(255) NOT NULL,
    member_role ENUM('ADMIN', 'USER') NOT NULL,
    member_gender VARCHAR(10),
    member_tel VARCHAR(20) NOT NULL,
    member_postcode VARCHAR(10),
    member_address VARCHAR(255),
    member_address_detail VARCHAR(50),
    member_enable BOOLEAN DEFAULT TRUE,
    member_disabled_date DATETIME DEFAULT NULL,
    member_delete_date DATETIME DEFAULT NULL,
    member_profile INT DEFAULT 0,
    member_join_date DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Member_profile_files (
    file_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT,
    origin_file_name VARCHAR(255),
    stored_file_name VARCHAR(255),
    FOREIGN KEY (member_id) REFERENCES Member(member_id)
);

CREATE TABLE Campsite (
    camp_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    camp_name VARCHAR(255)  NOT NULL,
    camp_thumb INT DEFAULT 0,
    camp_info TEXT,
    camp_tel VARCHAR(20),
    camp_address TEXT,
    camp_toilet BOOLEAN,
    camp_sink BOOLEAN,
    camp_parking BOOLEAN,
    camp_total_capacity INT,
    camp_electric BOOLEAN,
    camp_posting_date DATETIME,
    camp_hit INT DEFAULT 0,
    camp_like INT DEFAULT 0
);

CREATE TABLE Camp_thumb_files (
    file_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    camp_id BIGINT,
    origin_file_name VARCHAR(255),
    stored_file_name VARCHAR(255),
    FOREIGN KEY (camp_id) REFERENCES Campsite(camp_id)
);

CREATE TABLE Camp_capacity (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    date DATE,
    camp_id BIGINT,
    camp_current_capacity INT DEFAULT 0,
    FOREIGN KEY (camp_id) REFERENCES Campsite(camp_id)
);
-- fk로 사용하기위해 인덱스 추가
ALTER TABLE Camp_capacity ADD INDEX (date);


CREATE TABLE Reservation (
    res_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT,
    camp_id BIGINT,
	res_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    usage_date DATE,
    payment_type ENUM('CREDIT_CARD', 'BANK_TRANSFER', 'NAVERPAY'),
    payment_status BOOLEAN DEFAULT false,
    FOREIGN KEY (member_id) REFERENCES Member(member_id),
    FOREIGN KEY (camp_id) REFERENCES Campsite(camp_id),
    FOREIGN KEY (usage_date) REFERENCES Camp_capacity(date)
);

CREATE TABLE Review (
    review_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT,
    camp_id BIGINT,
    review_title VARCHAR(255) NOT NULL,
    review_content TEXT,
    review_posting_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    review_editing_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    review_images INT DEFAULT 0,
    FOREIGN KEY (member_id) REFERENCES Member(member_id),
    FOREIGN KEY (camp_id) REFERENCES Campsite(camp_id)
);

CREATE TABLE Review_files (
    file_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    review_id BIGINT,
    origin_file_name VARCHAR(255),
    stored_file_name VARCHAR(255),
    FOREIGN KEY (review_id) REFERENCES Review(review_id)
);

CREATE TABLE Comment (
    comment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT,
    review_id BIGINT,
    content VARCHAR(100) NOT NULL,
    FOREIGN KEY (member_id) REFERENCES Member(member_id),
    FOREIGN KEY (review_id) REFERENCES Review(review_id)
);

CREATE TABLE Chat_room (
    chat_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member1_id BIGINT,
    member2_id BIGINT,
    create_at DATETIME,
    FOREIGN KEY (member1_id) REFERENCES Member(member_id),
    FOREIGN KEY (member2_id) REFERENCES Member(member_id)
);

CREATE TABLE Chat_message (
    chat_msg_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    chat_id BIGINT,
    member_id BIGINT,
    content TEXT,
    create_at DATETIME,
    FOREIGN KEY (chat_id) REFERENCES Chat_room(chat_id),
    FOREIGN KEY (member_id) REFERENCES Member(member_id)
);

CREATE TABLE Camp_files (
    file_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    camp_id BIGINT,
    origin_file_name VARCHAR(255),
    stored_file_name VARCHAR(255),
    FOREIGN KEY (camp_id) REFERENCES Campsite(camp_id)
);


-- 트리거
-- 예약 추가시 camp_current_capacity 증가
DELIMITER $$

CREATE TRIGGER update_capacity_after_reservation
AFTER INSERT ON Reservation
FOR EACH ROW
BEGIN
    UPDATE Camp_capacity
    SET camp_current_capacity = camp_current_capacity + 1
    WHERE camp_id = NEW.camp_id AND date = NEW.usage_date;
END $$

DELIMITER ;

-- 예약 취소시 camp_current_capacity 감소
DELIMITER $$

CREATE TRIGGER update_capacity_after_cancellation
AFTER DELETE ON Reservation
FOR EACH ROW
BEGIN
    UPDATE Camp_capacity
    SET camp_current_capacity = camp_current_capacity - 1
    WHERE camp_id = OLD.camp_id AND date = OLD.usage_date;
END $$

DELIMITER ;


