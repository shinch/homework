DROP TABLE IF EXISTS keyword CASCADE;
CREATE TABLE keyword (
    seq bigint generated by default as identity,
    keyword varchar(150) NOT NULL,
    cnt int NOT NULL DEFAULT 0,
    primary key (seq)
);
CREATE UNIQUE INDEX UK_KEYWORD ON keyword(keyword);
CREATE INDEX IDX_CNT_KEYWORD ON keyword(cnt, keyword);