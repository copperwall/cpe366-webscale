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
-- Name: day_off_requests; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE day_off_requests (
    day_off_requestid integer NOT NULL,
    employeeid integer,
    date date,
    type dayoff_type
);


ALTER TABLE day_off_requests OWNER TO postgres;

--
-- Name: day_off_requests_day_off_requestid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE day_off_requests_day_off_requestid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE day_off_requests_day_off_requestid_seq OWNER TO postgres;

--
-- Name: day_off_requests_day_off_requestid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE day_off_requests_day_off_requestid_seq OWNED BY day_off_requests.day_off_requestid;


--
-- Name: employee_shifts; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE employee_shifts (
    employee_shiftid integer NOT NULL,
    employeeid integer NOT NULL,
    shiftid integer NOT NULL,
    requested integer DEFAULT 0 NOT NULL,
    date timestamp without time zone NOT NULL
);


ALTER TABLE employee_shifts OWNER TO postgres;

--
-- Name: COLUMN employee_shifts.requested; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN employee_shifts.requested IS 'Whether or not the shift was requested by a human or assigned by the algorithm.';


--
-- Name: employee_shifts_employee_shiftid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE employee_shifts_employee_shiftid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE employee_shifts_employee_shiftid_seq OWNER TO postgres;

--
-- Name: employee_shifts_employee_shiftid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE employee_shifts_employee_shiftid_seq OWNED BY employee_shifts.employee_shiftid;


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
-- Name: shifts; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE shifts (
    shiftid integer NOT NULL,
    day_of_week day_of_week NOT NULL,
    time_of_day time_of_day NOT NULL,
    shift_type shift_type NOT NULL,
    weekid integer DEFAULT 1 NOT NULL
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
-- Name: weeks; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE weeks (
    weekid integer NOT NULL,
    start_date date NOT NULL
);


ALTER TABLE weeks OWNER TO postgres;

--
-- Name: weeks_weekid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE weeks_weekid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE weeks_weekid_seq OWNER TO postgres;

--
-- Name: weeks_weekid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE weeks_weekid_seq OWNED BY weeks.weekid;


--
-- Name: day_off_requestid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY day_off_requests ALTER COLUMN day_off_requestid SET DEFAULT nextval('day_off_requests_day_off_requestid_seq'::regclass);


--
-- Name: employee_shiftid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY employee_shifts ALTER COLUMN employee_shiftid SET DEFAULT nextval('employee_shifts_employee_shiftid_seq'::regclass);


--
-- Name: employeeid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY employees ALTER COLUMN employeeid SET DEFAULT nextval('employees_employeeid_seq'::regclass);


--
-- Name: shiftid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY shifts ALTER COLUMN shiftid SET DEFAULT nextval('shifts_shiftid_seq'::regclass);


--
-- Name: weekid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY weeks ALTER COLUMN weekid SET DEFAULT nextval('weeks_weekid_seq'::regclass);


--
-- Name: day_off_requests_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY day_off_requests
    ADD CONSTRAINT day_off_requests_pkey PRIMARY KEY (day_off_requestid);


--
-- Name: employee_shifts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY employee_shifts
    ADD CONSTRAINT employee_shifts_pkey PRIMARY KEY (employee_shiftid);


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
-- Name: weeks_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY weeks
    ADD CONSTRAINT weeks_pkey PRIMARY KEY (weekid);


--
-- Name: day_off_request_fk1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY day_off_requests
    ADD CONSTRAINT day_off_request_fk1 FOREIGN KEY (employeeid) REFERENCES employees(employeeid);


--
-- Name: employee_shift_employeeid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY employee_shifts
    ADD CONSTRAINT employee_shift_employeeid FOREIGN KEY (employeeid) REFERENCES employees(employeeid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: employee_shifts_shiftid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY employee_shifts
    ADD CONSTRAINT employee_shifts_shiftid FOREIGN KEY (shiftid) REFERENCES shifts(shiftid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: shifts_fk1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY shifts
    ADD CONSTRAINT shifts_fk1 FOREIGN KEY (weekid) REFERENCES weeks(weekid);


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

