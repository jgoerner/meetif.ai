import React, {useEffect, useState} from "react";
import Styles from "./Main.module.scss";
import PersonPicker from "../../organisms/PersonPicker/PersonPicker";
import {
    APP_TITLE,
    COMMON_EVENTS, CONNECTION, DESCRIPTION,
    DIFFERENT_COUNTRY,
    DUMMY_PERSON,
    NO_COMMON_EVENTS, NO_CONNECTION,
    NO_DIFFERENT_COUNTRY
} from "../../misc/Constants";
import {IPersonCard} from "../../molecules/PersonCard/PersonCard";
import axios from "axios";
import Button from "../../atoms/Button/Button";
import Modal from "../../molecules/Modal/Modal";
import Infobar from "../../atoms/Infobar/Infobar";
import Intention from "../../misc/Enums";
import Avatar from "../../atoms/Avatar/Avatar";

const Main = () => {

    const emptyPersons = new Map([[0, DUMMY_PERSON], [1, DUMMY_PERSON]]);
    const [persons, setPersons] = useState<Map<number, IPersonCard>>(emptyPersons);
    const [untouched, setUntouched] = useState(true);
    const [allPersons, setAllPersons] = useState<IPersonCard[]>([]);
    const [empty, setEmpty] = useState(true);
    const [showModal, setShowModal] = useState(false);
    const [modalContent, setModalContent] = useState<Array<JSX.Element>>([]);

    useEffect(() => {
        if (untouched) {
            setUntouched(false);
            axios.get("http://localhost:9090/api/persons")
                .then(r => setAllPersons([...r["data"]]))
                .catch(e => console.log(`Got the error ${e}`))
        }
    });

    const handleSubmit = () => {
        // toggle modal
        setShowModal(true);

        // convert state ids to form data
        const ids = Array.from(persons).map(([, person]) => person.id);
        const eventForm = new FormData();
        eventForm.append("p1", ids[0].toString());
        eventForm.append("p2", ids[1].toString() );
        axios.post("http://localhost:9090/api/icebreaker/commonEvents", eventForm)
            .then(r => {
                if (r["data"].length == 0) {
                    setModalContent(prevState => {
                        const newContent = [...prevState];
                        newContent.push(<Infobar intention={Intention.BAD}><p>{NO_COMMON_EVENTS}</p></Infobar>);
                        return newContent;
                    });
                    const countryForm = new FormData();
                    const cities = Array.from(persons).map(([, person]) => person.location);
                    countryForm.append("city1", cities[0]);
                    countryForm.append("city2", cities[1]);
                    axios.post("http://localhost:9090/api/icebreaker/differentCountry", countryForm)
                        .then(r => {
                            if (r["data"]) {
                                setModalContent(prevState => {
                                    const newContent = [...prevState];
                                    newContent.push(
                                        <Infobar intention={Intention.GOOD}>
                                            <p>{DIFFERENT_COUNTRY}</p>
                                            <p>Talk about the differences between <b>{cities[0]}</b> and <b>{cities[1]}</b></p>
                                        </Infobar>);
                                    return newContent;
                                });
                            } else {
                                setModalContent(prevState => {
                                    const newContent = [...prevState];
                                    newContent.push(<Infobar intention={Intention.BAD}><p>{NO_DIFFERENT_COUNTRY}</p></Infobar>);
                                    return newContent;
                                });
                                const names = Array.from(persons).map(([, person]) => person.name);
                                const connectionForm = new FormData();
                                connectionForm.append("fromName", names[0].toString());
                                connectionForm.append("toName", names[1].toString() );
                                axios.post("http://localhost:9090/api/icebreaker/connection", connectionForm)
                                    .then(r => {
                                        if (r["data"].length != 0) {
                                            console.log(r["data"]);
                                            setModalContent( prevState => {
                                                const newContent = [...prevState];
                                                const tmp = [<p>{CONNECTION}</p>];
                                                Object.keys(r["data"]).map((key, idx) =>{
                                                    if (idx % 2 == 0) {
                                                        tmp.push(
                                                            <div className={Styles.PersonInfoBar}>
                                                                <Infobar intention={Intention.NEUTRAL}>
                                                                    <div className={Styles.InfobarContainer}>
                                                                        <Avatar mini imgURL={r["data"][key]["imgURL"]}/>
                                                                        <p>{r["data"][key]["name"]}</p>
                                                                    </div>
                                                                </Infobar>
                                                            </div>)
                                                    } else {
                                                        tmp.push(
                                                            <div className={Styles.EventInfoBar}>
                                                                <Infobar intention={Intention.NEUTRAL}>
                                                                    <div className={Styles.InfobarContainer}>
                                                                        <p>{r["data"][key]["name"]}</p>
                                                                    </div>
                                                                </Infobar>
                                                            </div>)
                                                    }
                                                });
                                                newContent.push(<Infobar intention={Intention.GOOD}>{tmp}</Infobar>);
                                                return newContent;
                                            });

                                        } else {
                                            setModalContent(prevState => {
                                                const newContent = [...prevState];
                                                newContent.push(<Infobar intention={Intention.BAD}><p>{NO_CONNECTION}</p></Infobar>);
                                                return newContent;
                                            });
                                            axios.get("http://numbersapi.com/random/")
                                                .then(r => setModalContent(prevState => {
                                                    const newContent = [...prevState];
                                                    newContent.push(<Infobar intention={Intention.GOOD}><p>Did you know:</p><p>{r["data"]}</p></Infobar>);
                                                    return newContent;
                                                }));
                                        }
                                    })
                                    .catch(e => console.log(`Got the error ${e}`))

                            }
                        })
                        .catch(e => console.log("Error in second icebreaker - " + e))
                } else {
                    console.log(r["data"]);
                    const events = r["data"].map((d: any) => d.name);
                    setModalContent([
                        <Infobar intention={Intention.GOOD}>
                            <p>{COMMON_EVENTS}</p><p>Start talking about events like {events.join(", ")}</p>
                        </Infobar>
                    ]);
                }
            })
            .catch(e => console.log("Error in first icebreaker - " + e))
    };

    const handleReset = () => {
        setPersons(emptyPersons);
        setEmpty(true);
    };

    const handlePersonPick = (p: number, idx: number) => {
        const pCopy = new Map(persons);
        pCopy.set(idx, allPersons[p]);
        setPersons(pCopy);
    };

    const handleRandomPick = () => {
        const p1 = Math.floor(Math.random()*allPersons.length);
        const p2 = Math.floor(Math.random()*allPersons.length);
        const pCopy = new Map(persons);
        pCopy.set(0, allPersons[p1]);
        pCopy.set(1, allPersons[p2]);
        setPersons(pCopy);
        setEmpty(false);
    };

    const closeModal = () => {
        setShowModal(!showModal);
        setModalContent([]);
    };

    let classesPersonPicker = [Styles.PersonPicker];
    let classesCardContent = [Styles.CardContent];
    let explainingText;
    if (empty) {
        classesPersonPicker.push(Styles.Disabled);
        classesCardContent.push(Styles.EmptyContent);
        explainingText = <div className={Styles.Explanation}>{DESCRIPTION}</div>
    }

    let ctx = (
        <React.Fragment>
            <div className={Styles.Container}>
                <Modal text={""} visible={showModal} clickHandler={closeModal}>
                    {modalContent}
                </Modal>
                <div className={Styles.Card}>
                    <div className={Styles.CardHeading}>
                        <p>{APP_TITLE}</p>
                    </div>
                    <div className={classesCardContent.join(" ")}>
                        {explainingText}
                        <div className={classesPersonPicker.join(" ")}>
                            <PersonPicker
                                handlePersonPicked={(p: number) => handlePersonPick(p, 0)}
                                persons={allPersons}
                                picked={persons.get(0)}
                            />
                            <PersonPicker
                                handlePersonPicked={(p: number) => handlePersonPick(p, 1)}
                                persons={allPersons}
                                picked={persons.get(1)}
                            />
                        </div>
                    </div>
                    <div className={Styles.ButtonBar}>
                        <Button text="Reset" action={handleReset} enabled/>
                        <Button text="Random Pair" action={handleRandomPick} enabled/>
                        <Button text="Icebreaker" action={handleSubmit} enabled={!empty}/>
                    </div>
                </div>
            </div>
        </React.Fragment>

    );
    return ctx;
};

export default Main;