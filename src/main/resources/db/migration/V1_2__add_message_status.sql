ALTER TABLE "message"
    ADD COLUMN receive_status varchar(64) default 'RECEIVED',
    ADD COLUMN send_status varchar(64) default 'SENT';

