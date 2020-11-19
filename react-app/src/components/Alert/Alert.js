import React from "react";
import "./Alert.css";

const Alert = (props) => {
  return <div className="alert">{props.message}</div>;
};

export default Alert;
