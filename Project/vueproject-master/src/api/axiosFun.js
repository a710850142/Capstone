import axios from 'axios';

// 通用公用方法
const req = (method, url, params) => {
    return axios({
        method: method,
        url: url,
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            token: localStorage.getItem('logintoken')
        },
        data: params,
        traditional: true,
        transformRequest: [
            function(data) {
                let ret = ''
                for (let it in data) {
                    ret +=
                        encodeURIComponent(it) +
                        '=' +
                        encodeURIComponent(data[it]) +
                        '&'
                }
                return ret
            }
        ]
    }).then(res => res.data);
};

const loginreq = ( url, params) => {
  return axios({
    method: 'post',
    url: url,
    data: params,
    traditional: true
  }).then(res => res.data);
};

const reqs = ( url, params) => {
    return axios({
      method: 'post',
      url: url,
      headers: {
        'Content-Type': 'application/json;utf-8',
    },
      data: params,
      traditional: true
    }).then(res => res.data);
  };

  const qq = (url) => {
    return axios({
      method: 'post',
      url: url,
      headers: {
        'Content-Type': 'application/json;utf-8',
    },
       data:null,
      traditional: true
    }).then(res => res.data);
  };

export {
    loginreq,
    req,
    reqs,
    qq,
  reqPost
}