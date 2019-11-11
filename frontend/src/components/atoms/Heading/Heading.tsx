import React from "react";
import styles from "./Heading.module.scss";

interface IProps {
    text: string
}


const Heading = (props: IProps) => {
    return <h1 className={styles.heading}>{props.text}</h1>
};

export default Heading;