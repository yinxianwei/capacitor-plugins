import { Wechat } from '@yinxianwei&#x2F;wechat';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    Wechat.echo({ value: inputValue })
}
