const APP_TITLE = "Meetif.ai";


const DUMMY_PERSON = {
    id: 0,
    name: "",
    location: "",
    participatedEvents: 0,
    imgURL: "",
    mini: false
};

const COMMON_EVENTS = "Yeah, you might have met before";
const NO_COMMON_EVENTS = "There is no event both of you have participated in";
const DIFFERENT_COUNTRY = "Interesting, you come from different countries";
const NO_DIFFERENT_COUNTRY = "You are coming from the same country";
const CONNECTION = "Interesting, the following combination people & events connects you";
const NO_CONNECTION = "There is no connection via other people between you";
const DESCRIPTION = "Welcome to Meetif.ai - your social Knowledge Graph that helps you connecting with like-minded people";

export {
    APP_TITLE,
    COMMON_EVENTS,
    CONNECTION,
    DESCRIPTION,
    DIFFERENT_COUNTRY,
    DUMMY_PERSON,
    NO_CONNECTION,
    NO_COMMON_EVENTS,
    NO_DIFFERENT_COUNTRY
}