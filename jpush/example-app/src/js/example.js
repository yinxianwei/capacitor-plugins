import { JPush } from '@yinxianwei&#x2F;jpush';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    JPush.echo({ value: inputValue })
}
