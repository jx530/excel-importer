import { Modal, Space, message } from "antd";
import { useEffect, useState } from "react";

export const syncData = async (id) => {
    const events = new EventSource(`${process.env.REACT_APP_API_BASE_URL}/sync/${id}`);
    return new Promise((resolve) => {
        const ModelContent = () => {
            const [progress, setProgress] = useState({});
            const [resps, setResps] = useState([]);
            useEffect(() => {
                events.onmessage = event => {
                    const parsedData = JSON.parse(event.data);
                    console.log(parsedData)
                    switch (parsedData.type) {
                        case "queue":
                        case "progress":
                            const { progress, response } = parsedData.data;
                            const { progress: { current, latest } } = progress
                            setProgress({ current, latest })
                            if (response) {
                                setResps((resp) => [...resp, response]);
                            }
                            console.log(progress, response);
                            break;
                        case "finish":
                            message.info(parsedData.data)
                            break;
                        default:
                            break;
                    }
                };
            })
            const { current, latest } = progress;
            return <Space direction="vertical">
                {current && latest ? <div>{current}/{latest}</div> : null}
                {resps.length ? resps.map(r => <div>{r}</div>) : null}
            </Space>
        }
        const modal = Modal.confirm({
            title: '数据同步中',
            closable: true,
            width: 600,
            footer: null,
            content: <ModelContent />
        })
    })
}