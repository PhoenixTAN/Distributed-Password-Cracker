import React from "react";
import "./Cracker.css";
import { useState, useRef } from "react";
import CryptoJS from "crypto-js";
import Alert from "../Alert/Alert";
import Axios from "axios";

const Cracker = () => {
  const MAX_NUM_OF_WORKERS = 8;
  
  const PASSWORD_LENGTH = 5;
  const MD5_LENGTH = 32;
  const MANAGEMENT_SERVER_URL = "http://192.86.139.65:8080/getPassword";
  const REQUEST_DELAY = 1500; // ms
  const REQUEST_TIMEOUT = 10000; // ms

  const [showAlert, setAlert] = useState(false);
  const [alertMessage, setAlertMessage] = useState("");
  const [passwordTips, setPasswordTips] = useState("");
  const [md5Tips, setMd5Tips] = useState("");
  const [numOfWorkersTips, setNumOfWorkersTips] = useState("");
  const [status, setStatus] = useState("none");
  const [correctPassword, setCorrectPassword] = useState("");
  const [disableCrack, setDisableCracker] = useState(false);

  const passwordRef = useRef();
  const md5Ref = useRef();
  const numOfWorkersRef = useRef();

  const passwordOnChange = (event) => {
    const password = event.target.value;
    if (password.length === 0) {
      setPasswordTips("");
      return;
    }

    if (password.length !== PASSWORD_LENGTH) {
      setPasswordTips(
        "Warning! The password length should be " +
          PASSWORD_LENGTH +
          " characters long."
      );
      return;
    }

    if (password.length === PASSWORD_LENGTH) {
      for (let i = 0; i < password.length; i++) {
        if (
          !(
            (password[i] >= "a" && password[i] <= "z") ||
            (password[i] >= "A" && password[i] <= "Z")
          )
        ) {
          setPasswordTips("Warning! The password should be English letters.");
          return;
        }
      }
      setPasswordTips("");
      md5Ref.current.value = CryptoJS.MD5(password).toString();
      setMd5Tips("");
    }
  };

  const md5OnChange = (event) => {
    const md5 = event.target.value;

    if (md5.length === 0) {
      setMd5Tips("");
      return;
    }

    if (md5.length !== MD5_LENGTH) {
      setMd5Tips("Warning! The md5 has a length of " + MD5_LENGTH + ".");
      return;
    }

    for (let i = 0; i < md5.length; i++) {
      if (
        !(
          (md5[i] >= "a" && md5[i] <= "z") ||
          (md5[i] >= "A" && md5[i] <= "Z")
        ) &&
        !(md5[i] < "0" && md5[i] > "9")
      ) {
        setMd5Tips(
          "Warning! The md5 should be either English letters or numbers."
        );
        return;
      }
    }

    setMd5Tips("");
  };

  const numOfWorkersOnChange = (event) => {
    const numOfWorkers = parseInt(event.target.value);

    if (event.target.value.length === 0) {
      setNumOfWorkersTips("");
      return;
    }

    if (isNaN(numOfWorkers)) {
      setNumOfWorkersTips("Warning! Not a number.");
      return;
    }

    if (numOfWorkers <= 0) {
      setNumOfWorkersTips("Warning! We need positive integer.");
      return;
    }

    if (numOfWorkers > MAX_NUM_OF_WORKERS) {
      setNumOfWorkersTips(
        "Warning! Max number of workers is " + PASSWORD_LENGTH + "."
      );
      return;
    }

    setNumOfWorkersTips("");
  };

  const onProceedClear = () => {
    passwordRef.current.value = "";
    md5Ref.current.value = "";
    numOfWorkersRef.current.value = "";
  };

  const onProceedCrack = () => {
    if (
      passwordTips.length !== 0 ||
      md5Tips.length !== 0 ||
      numOfWorkersTips.length !== 0 ||
      md5Ref.current.value.length === 0 ||
      numOfWorkersRef.current.value.length === 0
    ) {
      setAlertMessage("Please input the correct settings.");
      setAlert(true);
      setTimeout(() => {
        setAlert(false);
      }, 3500);
      return;
    }

    // disable the crack button
    setDisableCracker(true);

    // request timeout
    const requestTimeout = setTimeout(() => {
      setStatus("Request timeout.");
    }, REQUEST_TIMEOUT);

    // send post request
    setStatus("Request has been sent.");
    
    const body = {
      MD5Password: md5Ref.current.value,
      workerNum: numOfWorkersRef.current.value,
    };
    console.log(body);
    Axios.post(MANAGEMENT_SERVER_URL, body)
      .then((res) => {
        console.log(res);
        setStatus("receive correct password");
        setCorrectPassword(res.data);
        clearTimeout(requestTimeout);
        setTimeout(() => {
          setDisableCracker(false);
        }, REQUEST_DELAY);
      })
      .catch((error) => {
        console.log(error);
        setStatus("Oops! Something happened!");
        clearTimeout(requestTimeout);
        setTimeout(() => {
          setDisableCracker(false);
        }, REQUEST_DELAY);
      });
  };

  return (
    <>
      <section className="cracker">
        <div className="form">
          <label>
            Password (optional. {PASSWORD_LENGTH} characters a~z, A~z)
          </label>
          <input
            ref={passwordRef}
            onChange={(event) => passwordOnChange(event)}
            spellCheck="false"
          />
          <div className="tips">{passwordTips}</div>

          <label>MD5 (Required, {MD5_LENGTH} bits)</label>
          <input
            ref={md5Ref}
            onChange={(event) => md5OnChange(event)}
            spellCheck="false"
          />
          <div className="tips">{md5Tips}</div>

          <label>Number of workers (Required, max: {MAX_NUM_OF_WORKERS})</label>
          <input
            ref={numOfWorkersRef}
            onChange={(event) => numOfWorkersOnChange(event)}
            spellCheck="false"
          />
          <div className="tips">{numOfWorkersTips}</div>
        </div>

        <div className="result">
          <div>Status: {status}</div>
          <div>The password behind md5: {correctPassword}</div>
        </div>

        <div className="submit">
          <button
            onClick={(event) => onProceedCrack(event)}
            disabled={disableCrack}
          >
            Crack
          </button>
          <button onClick={onProceedClear}>Clear</button>
        </div>
      </section>
      {showAlert && <Alert message={alertMessage} />}
    </>
  );
};

export default Cracker;
