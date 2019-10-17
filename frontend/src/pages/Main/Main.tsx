import React, { useState } from "react";
import Styles from "./Main.module.css";
import PersonPicker from "../../organisms/PersonPicker/PersonPicker";
import { DummyDescription, DummyPerson, DummyPersons } from "../../misc/Constants";
import {IPersonCard} from "../../molecules/PersonCard/PersonCard";

const Main = () => {

    const emptyPersons = new Map([[0, DummyPerson], [1, DummyPerson]]);

    const [persons, setPersons] = useState<Map<number, IPersonCard>>(emptyPersons);

    const handleSubmit = () => {
        alert(`Doing crazy stuff with ${ Array.from(persons).map( ([_, v]) => JSON.stringify(v))}`)
    };

    const handleReset = () => {
        setPersons(emptyPersons);
    };

    const handlePersonPick = (p: number, idx: number) => {
        const pCopy = new Map(persons);
        pCopy.set(idx, DummyPersons[p]);
        setPersons(pCopy);
    };


    let content = (
        <div className={Styles.Container}>
            <div />
            <div className={Styles.Content}>
                <h1>Here Comes the Heading</h1>
                <p>{DummyDescription}</p>
                <div className={Styles.PersonPicker}>
                    <PersonPicker
                        handlePersonPicked={(p: number) => handlePersonPick(p, 0)}
                        persons={DummyPersons}
                        picked={persons.get(0)}
                    />
                    <PersonPicker
                        handlePersonPicked={(p: number) => handlePersonPick(p, 1)}
                        persons={DummyPersons}
                        picked={persons.get(1)}
                    />
                </div>
                <button onClick={handleSubmit}>Go</button>
                <button onClick={handleReset}>Reset</button>
            </div>
            <div />
        </div>
    );
    return content
};

export default Main;