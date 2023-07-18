import { message } from "antd";
import _ from "lodash";

export const BASE_URL = process.env.REACT_APP_API_BASE_URL || '/';

function absoluteUrl(url, params) {
  url = _.first(url) === "/" ? url : "/" + url;
  const search = _.isEmpty(params)
    ? ""
    : "?" + new URLSearchParams(params).toString();
  return `${BASE_URL}${url}${search}`;
}

export class Ajax {
  url;
  params;
  body;
  headers;
  method = "GET";
  constructor(url) {
    this.url = url;
    this.headers = {
      "Content-Type": "application/json",
    };
  }

  path(child) {
    let baseUrl = this.url;
    if (!baseUrl) {
      this.url = child;
      return this;
    }
    if (_.last(baseUrl) === "/") {
      baseUrl = baseUrl.slice(0, -1);
    }
    this.url = _.head(child) === "/" ? baseUrl + child : baseUrl + "/" + child;
    return this;
  }

  query(queryObj) {
    this.params = queryObj;
    return this;
  }

  payload(payload) {
    this.body = JSON.stringify(payload);
    return this;
  }

  addHeader(key, value) {
    this.headers = _.assign(this.headers, { [key]: value });
    return this;
  }

  get() {
    this.method = "GET";
    return this.fetch();
  }

  post() {
    this.method = "POST";
    return this.fetch();
  }

  delete() {
    this.method = "DELETE";
    return this.fetch();
  }

  put() {
    this.method = "PUT";
    return this.fetch();
  }

  patch() {
    this.method = "PATCH";
    return this.fetch();
  }

  async fetch() {
    const { url, params, ...rest } = this;
    try {
      return await fetch(absoluteUrl(url, params), rest);
    } catch (e) {
      message.error("请检查网络是否正常");
      throw e;
    }
  }
}

export default function ajax(url) {
  return new Ajax(url);
}
