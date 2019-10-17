import React, { useState } from "react";
import { IPersonCard } from "../../molecules/PersonCard/PersonCard";
import PersonCard from "../../molecules/PersonCard/PersonCard";

interface IPersonPicker {
    persons: IPersonCard[],
    picked?: IPersonCard,
    handlePersonPicked: (p: number) => void
}

const PersonPicker = (props: IPersonPicker) => {

    const handlePersonPick = () => {
        const p = Math.floor(Math.random()*props.persons.length);
        //setPicked(p);
        props.handlePersonPicked(p);
    };

    let content;
    if (props.picked == undefined || props.picked.name == "") {
        content = <button onClick={handlePersonPick}>Please pick a person</button>
    } else {
        content = <PersonCard {...props.picked} />
    }
    return content
};

export default PersonPicker;