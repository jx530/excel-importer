import { Table } from "antd";
import { useEffect, useState } from 'react';
import './App.css';
import { list } from "./service/api";

const columns = [{
  dataIndex: 'id'
}, {
  dataIndex: 'table'
}, {
  dataIndex: 'progress'
}, {
  dataIndex: 'finished'
}, {
  dataIndex: 'created'
}, {
  dataIndex: 'modified'
}]

function App() {
  const [query, setQuery] = useState({
    page: 1,
    size: 10
  });
  const [loading, setLoading] = useState(true);
  const [pageData, setPageData] = useState();
  const { page, size } = query;
  useEffect(() => {
    setLoading(true)
    const pageData = list(page, size)
    setPageData(pageData);
    setLoading(false);
  }, [page, size])
  return (
    <div className="App">
      <Table loading={loading} dataSource={pageData?.content} columns={columns}
        pagination={{
          showSizeChanger: true,
          showTotal: true,
          pageSize: size,
          total: pageData?.total,
          current: page,
          onChange: (page, size) => setQuery({ page, size })
        }} />
    </div>
  );
}

export default App;
