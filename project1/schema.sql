--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- Name: day_of_week; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE day_of_week AS ENUM (
    'MONDAY',
    'TUESDAY',
    'WEDNESDAY',
    'THURSDAY',
    'FRIDAY',
    'SATURDAY',
    'SUNDAY'
);


ALTER TYPE day_of_week OWNER TO postgres;

--
-- Name: dayoff_type; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE dayoff_type AS ENUM (
    'sick',
    'vacation'
);


ALTER TYPE dayoff_type OWNER TO postgres;

--
-- Name: role_enum; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE role_enum AS ENUM (
    'admin',
    'doctor',
    'technician'
);


ALTER TYPE role_enum OWNER TO postgres;

--
-- Name: shift_type; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE shift_type AS ENUM (
    'technician',
    'appointment',
    'surgery'
);


ALTER TYPE shift_type OWNER TO postgres;

--
-- Name: time_of_day; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE time_of_day AS ENUM (
    'EARLY_MORNING',
    'DAY',
    'LATE',
    'OVERNIGHT',
    'SUNDAY'
);


ALTER TYPE time_of_day OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: days_off; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE days_off (
    employeeid integer NOT NULL,
    date date NOT NULL,
    type dayoff_type
);


ALTER TABLE days_off OWNER TO postgres;

--
-- Name: employees; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE employees (
    employeeid integer NOT NULL,
    login text,
    password text,
    role role_enum
);


ALTER TABLE employees OWNER TO postgres;

--
-- Name: employees_employeeid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE employees_employeeid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE employees_employeeid_seq OWNER TO postgres;

--
-- Name: employees_employeeid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE employees_employeeid_seq OWNED BY employees.employeeid;


--
-- Name: employees_to_shifts; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE employees_to_shifts (
    employeeid integer,
    shiftid integer,
    date timestamp without time zone NOT NULL
);


ALTER TABLE employees_to_shifts OWNER TO postgres;

--
-- Name: shifts; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE shifts (
    shiftid integer NOT NULL,
    day_of_week day_of_week NOT NULL,
    time_of_day time_of_day NOT NULL,
    type shift_type NOT NULL
);


ALTER TABLE shifts OWNER TO postgres;

--
-- Name: shifts_shiftid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE shifts_shiftid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE shifts_shiftid_seq OWNER TO postgres;

--
-- Name: shifts_shiftid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE shifts_shiftid_seq OWNED BY shifts.shiftid;


--
-- Name: employeeid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY employees ALTER COLUMN employeeid SET DEFAULT nextval('employees_employeeid_seq'::regclass);


--
-- Name: shiftid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY shifts ALTER COLUMN shiftid SET DEFAULT nextval('shifts_shiftid_seq'::regclass);


--
-- Name: days_off_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY days_off
    ADD CONSTRAINT days_off_pkey PRIMARY KEY (employeeid, date);


--
-- Name: employees_login_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY employees
    ADD CONSTRAINT employees_login_key UNIQUE (login);


--
-- Name: employees_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY employees
    ADD CONSTRAINT employees_pkey PRIMARY KEY (employeeid);


--
-- Name: shifts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY shifts
    ADD CONSTRAINT shifts_pkey PRIMARY KEY (shiftid);


--
-- Name: days_off_employeeid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY days_off
    ADD CONSTRAINT days_off_employeeid_fkey FOREIGN KEY (employeeid) REFERENCES employees(employeeid);


--
-- Name: employees_to_shifts_employeeid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY employees_to_shifts
    ADD CONSTRAINT employees_to_shifts_employeeid_fkey FOREIGN KEY (employeeid) REFERENCES employees(employeeid);


--
-- Name: employees_to_shifts_shiftid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY employees_to_shifts
    ADD CONSTRAINT employees_to_shifts_shiftid_fkey FOREIGN KEY (shiftid) REFERENCES shifts(shiftid);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

