import { Space, Button, Modal, Table, Upload, message } from "antd";
import { useState } from "react";

const errorColumns = [{
    title: '行号',
    dataIndex: 'lineNum',
}, {
    title: '错误详情',
    dataIndex: 'errors',
    render: (errors) => {
        return errors.join(',')
    }
}];

export const uploadFile = async (type) => {
    return new Promise((resolve) => {
        const ModelContent = () => {
            const [errors, setErrors] = useState();
            return <Space direction="vertical">
                <Upload accept=".xlsx"
                    action={`/api/import/${type}`}
                    onSuccess={(body) => {
                        const { code, data } = body;
                        if (code === 200) {
                            message.info('上传成功')
                            modal.destroy();
                        } else if (code === 400) {
                            setErrors(data);
                            return false;
                        }
                    }}>
                    <Button>上传文件</Button>
                </Upload>
                {errors ? <Table style={{ marginLeft: -15 }} rowKey="lineNum" caption="excel存在错误" dataSource={errors} columns={errorColumns} /> : null}
            </Space>
        }
        const modal = Modal.confirm({
            title: '上传文件',
            closable: true,
            width: 600,
            footer: null,
            className: "exporter-modal",
            content: <ModelContent />
        })
    })
}