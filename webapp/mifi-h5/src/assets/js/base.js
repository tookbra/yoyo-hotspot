(function (doc, win) {
  let resizeEvt = 'onorientationchange' in window ? 'onorientationchange' : 'resize'
  let recalc = function () {
    let deviceWidth = document.documentElement.clientWidth
    if (deviceWidth > 414) deviceWidth = 414
    document.documentElement.style.fontSize = deviceWidth / 7.5 + 'px'
  }
  if (!doc.addEventListener) return
  win.addEventListener(resizeEvt, recalc, false)
  doc.addEventListener('DOMContentLoaded', recalc, false)
})(document, window)
