--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.3
-- Dumped by pg_dump version 9.5.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

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
-- Name: room_bed; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE room_bed AS ENUM (
    'double',
    'single'
);


ALTER TYPE room_bed OWNER TO postgres;

--
-- Name: room_view; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE room_view AS ENUM (
    'ocean',
    'pool'
);


ALTER TYPE room_view OWNER TO postgres;

--
-- Name: user_type; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE user_type AS ENUM (
    'admin',
    'employee',
    'customer'
);


ALTER TYPE user_type OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: bookings; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE bookings (
    bookingid integer NOT NULL,
    userid integer NOT NULL,
    cancelled integer DEFAULT 0 NOT NULL,
    confirmed integer,
    name text NOT NULL,
    checked_in integer DEFAULT 0 NOT NULL,
    checked_out integer DEFAULT 0 NOT NULL
);


ALTER TABLE bookings OWNER TO postgres;

--
-- Name: TABLE bookings; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE bookings IS 'A booking represents one trasaction for a user. ';


--
-- Name: COLUMN bookings.confirmed; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN bookings.confirmed IS 'Has the booking been confirmed?';


--
-- Name: COLUMN bookings.name; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN bookings.name IS 'A friendly name for this booking';


--
-- Name: bookings_bookingid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE bookings_bookingid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bookings_bookingid_seq OWNER TO postgres;

--
-- Name: bookings_bookingid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE bookings_bookingid_seq OWNED BY bookings.bookingid;


--
-- Name: charges; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE charges (
    chargeid integer NOT NULL,
    description text NOT NULL,
    amount real NOT NULL,
    bookingid integer NOT NULL
);


ALTER TABLE charges OWNER TO postgres;

--
-- Name: COLUMN charges.bookingid; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN charges.bookingid IS 'The bookign that this charge pertains to';


--
-- Name: charges_chargeid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE charges_chargeid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE charges_chargeid_seq OWNER TO postgres;

--
-- Name: charges_chargeid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE charges_chargeid_seq OWNED BY charges.chargeid;


--
-- Name: credit_cards; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE credit_cards (
    credit_cardid integer NOT NULL,
    number text,
    cvv text,
    expiration timestamp without time zone,
    userid integer NOT NULL,
    street_address text,
    state text,
    zip_code text
);


ALTER TABLE credit_cards OWNER TO postgres;

--
-- Name: credit_cards_credit_cardid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE credit_cards_credit_cardid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE credit_cards_credit_cardid_seq OWNER TO postgres;

--
-- Name: credit_cards_credit_cardid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE credit_cards_credit_cardid_seq OWNED BY credit_cards.credit_cardid;


--
-- Name: payments; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE payments (
    paymentid integer NOT NULL,
    bookingid integer NOT NULL,
    amount real NOT NULL,
    date timestamp without time zone NOT NULL
);


ALTER TABLE payments OWNER TO postgres;

--
-- Name: TABLE payments; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE payments IS 'Payments made toward a booking';


--
-- Name: COLUMN payments.bookingid; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN payments.bookingid IS 'The booking this payment is for';


--
-- Name: payments_paymentid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE payments_paymentid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE payments_paymentid_seq OWNER TO postgres;

--
-- Name: payments_paymentid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE payments_paymentid_seq OWNED BY payments.paymentid;


--
-- Name: room_bookings; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE room_bookings (
    room_bookingid integer NOT NULL,
    roomid integer NOT NULL,
    start_date timestamp without time zone NOT NULL,
    end_date timestamp without time zone NOT NULL,
    price real NOT NULL,
    bookingid integer NOT NULL
);


ALTER TABLE room_bookings OWNER TO postgres;

--
-- Name: TABLE room_bookings; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE room_bookings IS 'Rooms that are part of a booking';


--
-- Name: COLUMN room_bookings.price; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN room_bookings.price IS 'The total charge for the room for the duration of the stay. The nightly rate can fluctuate, so it''s easiest to use this to lock-in the rate for a reservation.';


--
-- Name: room_booking_room_bookingid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE room_booking_room_bookingid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE room_booking_room_bookingid_seq OWNER TO postgres;

--
-- Name: room_booking_room_bookingid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE room_booking_room_bookingid_seq OWNED BY room_bookings.room_bookingid;


--
-- Name: room_prices; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE room_prices (
    room_priceid integer NOT NULL,
    roomid integer NOT NULL,
    price real NOT NULL,
    start_date timestamp without time zone NOT NULL,
    end_date timestamp without time zone NOT NULL
);


ALTER TABLE room_prices OWNER TO postgres;

--
-- Name: TABLE room_prices; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE room_prices IS 'Room pricing';


--
-- Name: COLUMN room_prices.roomid; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN room_prices.roomid IS 'The room';


--
-- Name: COLUMN room_prices.price; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN room_prices.price IS 'The per-night price for the given room';


--
-- Name: COLUMN room_prices.start_date; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN room_prices.start_date IS 'The start date of the price';


--
-- Name: COLUMN room_prices.end_date; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN room_prices.end_date IS 'The end date of the price';


--
-- Name: room_prices_room_priceid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE room_prices_room_priceid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE room_prices_room_priceid_seq OWNER TO postgres;

--
-- Name: room_prices_room_priceid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE room_prices_room_priceid_seq OWNED BY room_prices.room_priceid;


--
-- Name: rooms; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE rooms (
    roomid integer NOT NULL,
    number integer NOT NULL,
    view_type room_view,
    bed_type room_bed
);


ALTER TABLE rooms OWNER TO postgres;

--
-- Name: rooms_roomid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE rooms_roomid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE rooms_roomid_seq OWNER TO postgres;

--
-- Name: rooms_roomid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE rooms_roomid_seq OWNED BY rooms.roomid;


--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE users (
    userid integer NOT NULL,
    login text,
    password text,
    type user_type DEFAULT 'customer'::user_type,
    firstname text,
    lastname text,
    email text
);


ALTER TABLE users OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_id_seq OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE users_id_seq OWNED BY users.userid;


--
-- Name: bookingid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY bookings ALTER COLUMN bookingid SET DEFAULT nextval('bookings_bookingid_seq'::regclass);


--
-- Name: chargeid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY charges ALTER COLUMN chargeid SET DEFAULT nextval('charges_chargeid_seq'::regclass);


--
-- Name: credit_cardid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY credit_cards ALTER COLUMN credit_cardid SET DEFAULT nextval('credit_cards_credit_cardid_seq'::regclass);


--
-- Name: paymentid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY payments ALTER COLUMN paymentid SET DEFAULT nextval('payments_paymentid_seq'::regclass);


--
-- Name: room_bookingid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY room_bookings ALTER COLUMN room_bookingid SET DEFAULT nextval('room_booking_room_bookingid_seq'::regclass);


--
-- Name: room_priceid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY room_prices ALTER COLUMN room_priceid SET DEFAULT nextval('room_prices_room_priceid_seq'::regclass);


--
-- Name: roomid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rooms ALTER COLUMN roomid SET DEFAULT nextval('rooms_roomid_seq'::regclass);


--
-- Name: userid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users ALTER COLUMN userid SET DEFAULT nextval('users_id_seq'::regclass);


--
-- Name: bookings_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY bookings
    ADD CONSTRAINT bookings_pkey PRIMARY KEY (bookingid);


--
-- Name: charges_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY charges
    ADD CONSTRAINT charges_pkey PRIMARY KEY (chargeid);


--
-- Name: credit_cards_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY credit_cards
    ADD CONSTRAINT credit_cards_pkey PRIMARY KEY (credit_cardid);


--
-- Name: payments_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY payments
    ADD CONSTRAINT payments_pkey PRIMARY KEY (paymentid);


--
-- Name: room_booking_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY room_bookings
    ADD CONSTRAINT room_booking_pkey PRIMARY KEY (room_bookingid);


--
-- Name: room_prices_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY room_prices
    ADD CONSTRAINT room_prices_pkey PRIMARY KEY (room_priceid);


--
-- Name: rooms_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rooms
    ADD CONSTRAINT rooms_pkey PRIMARY KEY (roomid);


--
-- Name: users_login_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_login_key UNIQUE (login);


--
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (userid);


--
-- Name: booking_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY bookings
    ADD CONSTRAINT booking_user FOREIGN KEY (userid) REFERENCES users(userid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: CONSTRAINT booking_user ON bookings; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON CONSTRAINT booking_user ON bookings IS 'The user making the booking';


--
-- Name: charge-booking; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY charges
    ADD CONSTRAINT "charge-booking" FOREIGN KEY (bookingid) REFERENCES bookings(bookingid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: payment-booking; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY payments
    ADD CONSTRAINT "payment-booking" FOREIGN KEY (bookingid) REFERENCES bookings(bookingid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: room-booking; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY room_bookings
    ADD CONSTRAINT "room-booking" FOREIGN KEY (roomid) REFERENCES rooms(roomid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: CONSTRAINT "room-booking" ON room_bookings; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON CONSTRAINT "room-booking" ON room_bookings IS 'The room to be booked';


--
-- Name: room-booking-bookingid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY room_bookings
    ADD CONSTRAINT "room-booking-bookingid" FOREIGN KEY (bookingid) REFERENCES bookings(bookingid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: room-price; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY room_prices
    ADD CONSTRAINT "room-price" FOREIGN KEY (roomid) REFERENCES rooms(roomid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: userid_creditcard; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY credit_cards
    ADD CONSTRAINT userid_creditcard FOREIGN KEY (userid) REFERENCES users(userid) ON UPDATE CASCADE ON DELETE CASCADE;


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

