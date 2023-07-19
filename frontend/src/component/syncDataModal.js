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
                            setProgress(progress)
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
            const { type, progress: { current, latest } = {} } = progress
            return <Space direction="vertical">
                {type ? <div>{type.title}正在同步中...</div> : null}
                {current && latest ? <div>当前进度：{current} / {latest}</div> : null}
                <div style={{ maxHeight: '40vh', overflowY: 'auto' }}>
                    <div>上传日志</div>
                    {resps.length ? resps.map(r => <div>{JSON.stringify(r)}</div>) : null}
                </div>
            </Space>
        }
        Modal.confirm({
            title: '数据同步中',
            closable: true,
            width: 600,
            footer: null,
            content: <ModelContent />
        })
    })
}