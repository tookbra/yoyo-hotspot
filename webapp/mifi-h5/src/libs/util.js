const util = {}

util.getUrlParam = (key) => {
  // 获取url中"?"符后的字串
  const url = location.search
  const theRequest = {}
  if (url.indexOf('?') !== -1) {
    const str = url.substr(1)
    const arr = str.split('&')
    for (let i = 0; i < arr.length; i += 1) {
      theRequest[arr[i].split('=')[0]] = unescape(arr[i].split('=')[1])
    }
  }
  return theRequest[key]
}

export default util
