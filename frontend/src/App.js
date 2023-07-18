import { Button, Space, Table } from "antd";
import { useEffect, useMemo, useState } from 'react';
import './App.css';
import { uploadFile } from "./component/uploadFileModal";
import { syncData } from "./component/syncDataModal";
import { list } from "./service/api";

function App() {
  const [query, setQuery] = useState({
    page: 1,
    size: 10
  });
  const [loading, setLoading] = useState(true);
  const [pageData, setPageData] = useState();
  const { page, size } = query;
  const fetchData = async (page, size) => {
    setLoading(true)
    const pageData = await list(page, size)
    setPageData(pageData);
    setLoading(false);
  };
  useEffect(() => {
    fetchData(page, size);
  }, [page, size])

  const columns = useMemo(() => {
    return [{
      title: '序号',
      dataIndex: 'id'
    }, {
      title: '表名',
      dataIndex: 'type',
      render: ({ name, title, template }) => {
        return <a target="_blank" rel="noreferrer" href={`${process.env.REACT_APP_API_BASE_URL || ''}/template/${template}`}>{title}</a>
      }
    }, {
      title: '进度',
      dataIndex: 'progress',
      render: ({ current, latest }) => {
        return `${current}/${latest}`
      }
    }, {
      title: '是否完成',
      dataIndex: 'finished',
      render: (finished) => finished ? '完成' : '未完成'
    }, {
      title: '创建时间',
      dataIndex: 'created'
    }, {
      title: '修改时间',
      dataIndex: 'modified'
    }, {
      title: '操作',
      render: (record) => {
        const handleUploadFile = () => {
          uploadFile(record.type);
        }
        const handleSync = async () => {
          syncData(record.id);
        }
        return (
          <Space>
            <Button onClick={handleUploadFile}>上传文件</Button>
            <Button onClick={handleSync}>同步数据</Button>
          </Space>
        );
      }
    }];
  }, []);
  return (
    <div className="App">
      <Table rowKey='id' loading={loading} dataSource={pageData?.content} columns={columns}
        pagination={{
          showSizeChanger: true,
          pageSize: size,
          total: pageData?.total,
          current: page,
          onChange: (page, size) => setQuery({ page, size })
        }} />
    </div>
  );
}

export default App;
