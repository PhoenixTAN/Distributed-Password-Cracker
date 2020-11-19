import React from "react";
import './Collaboration.css';

const Collaboration = () => {

  return (
    <section className="collaboration">
      <table>
        <thead>
          <tr>
            <th>Member</th>
            <th>BUID</th>
            <th>Email</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>Ziqi Tan</td>
            <td>U 88387934</td>
            <td>ziqi1756@bu.edu</td>
          </tr>
          <tr>
            <td>Xueyan Xia</td>
            <td>U 82450191</td>
            <td>xueyanx@bu.edu</td>
          </tr>
          <tr>
            <td>Kaijia You</td>
            <td>U 44518396</td>
            <td>caydenyo@bu.edu</td>
          </tr>
          <tr>
            <td>Jingzhou Xue</td>
            <td>U 10828768</td>
            <td>jxue@bu.edu</td>
          </tr>
        </tbody>
      </table>
    </section>
  );
};

export default Collaboration;
