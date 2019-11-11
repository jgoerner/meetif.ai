import React from "react";
import Styles from "./Button.module.scss";

interface IProps {
    text: string,
    enabled: boolean
    action: () => void
}

const Button = (props: IProps) => {
    let styles = [Styles.Button];
    let action;
    if (props.enabled){
        action = props.action;
        styles.push(Styles.Disabled);
    }
    return <button className={styles.join(" ")} onClick={action}>{props.text}</button>
};

export default Button;