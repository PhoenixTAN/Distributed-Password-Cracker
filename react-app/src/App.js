import "./App.css";

import Collaboration from './components/Collaboration/Collaboration';
import Cracker from './components/Cracker/Cracker';


const App = () => {

  return (
    <div className="App">
      <header>CS 655 Final Project -- Improved Password Cracker</header>

      <Collaboration/>

      <Cracker/>

      <section className="description">
        How to use? You can input either the password or the MD5. If you input
        the password, the MD5 will be generated automatically. When you click
        the "Crack" button, the crack request along with the MD5 and the number
        of workers will be sent. If you input both password and MD5, the cracker
        will send the MD5. Have fun!
      </section>

    </div>
  );
};

export default App;
