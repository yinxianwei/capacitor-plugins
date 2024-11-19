import { Navbar } from '@yinxianwei&#x2F;navbar';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    Navbar.echo({ value: inputValue })
}
