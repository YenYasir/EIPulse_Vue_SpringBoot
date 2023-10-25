/**
* v-dialogDragWidth 可拖動彈窗高度（右下角）
* 
*/

export default {
    bind(el) {
        const dragDom = el.querySelector('.el-dialog');
        const lineEl = document.createElement('div');
        lineEl.style = 'width: 6px; background: inherit; height: 10px; position: absolute; right: 0; bottom: 0; margin: auto; z-index: 1; cursor: nwse-resize;';
        lineEl.addEventListener('mousedown',
            function(e) {
                // 鼠標按下，計算當前元素距離可視區的距離
                const disX = e.clientX - el.offsetLeft;
                const disY = e.clientY - el.offsetTop;
                // 當前寬度 高度
                const curWidth = dragDom.offsetWidth;
                const curHeight = dragDom.offsetHeight;
                document.onmousemove = function(e) {
                    e.preventDefault(); // 移動時禁用默認事件
                    // 通過事件委托，計算移動的距離
                    const xl = e.clientX - disX;
                    const yl = e.clientY - disY
                    dragDom.style.width = `${curWidth + xl}px`;
                    dragDom.style.height = `${curHeight + yl}px`;
                };
                document.onmouseup = function(e) {
                    document.onmousemove = null;
                    document.onmouseup = null;
                };
            }, false);
        dragDom.appendChild(lineEl);
    }
}