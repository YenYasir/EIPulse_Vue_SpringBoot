<template>
  <div class="editor" ref="editor" :style="styles"></div>
</template>

<script>
import Quill from "quill";
import "quill/dist/quill.core.css";
import "quill/dist/quill.snow.css";
import "quill/dist/quill.bubble.css";

export default {
name: "Editor",
props: {
  /* 編輯器的內容 */
  value: {
    type: String,
    default: "",
  },
  /* 高度 */
  height: {
    type: Number,
    default: null,
  },
  /* 最小高度 */
  minHeight: {
    type: Number,
    default: null,
  },
},
data() {
  return {
    Quill: null,
    currentValue: "",
    options: {
      theme: "snow",
      bounds: document.body,
      debug: "warn",
      modules: {
        // 工具欄配置
        toolbar: [
          ["bold", "italic", "underline", "strike"],       // 加粗 斜體 下劃線 刪除線
          ["blockquote", "code-block"],                    // 引用  代碼塊
          [{ list: "ordered" }, { list: "bullet" }],       // 有序、無序列表
          [{ indent: "-1" }, { indent: "+1" }],            // 縮進
          [{ size: ["small", false, "large", "huge"] }],   // 字體大小
          [{ header: [1, 2, 3, 4, 5, 6, false] }],         // 標題
          [{ color: [] }, { background: [] }],             // 字體顏色、字體背景顏色
          [{ align: [] }],                                 // 對齊方式
          ["clean"],                                       // 清除文本格式
          ["link", "image", "video"]                       // 連結、圖片、影片
        ],
      },
      placeholder: "請輸入內容",
      readOnly: false,
    },
  };
},
computed: {
  styles() {
    let style = {};
    if (this.minHeight) {
      style.minHeight = `${this.minHeight}px`;
    }
    if (this.height) {
      style.height = `${this.height}px`;
    }
    return style;
  },
},
watch: {
  value: {
    handler(val) {
      if (val !== this.currentValue) {
        this.currentValue = val === null ? "" : val;
        if (this.Quill) {
          this.Quill.pasteHTML(this.currentValue);
        }
      }
    },
    immediate: true,
  },
},
mounted() {
  this.init();
},
beforeDestroy() {
  this.Quill = null;
},
methods: {
  init() {
    const editor = this.$refs.editor;
    this.Quill = new Quill(editor, this.options);
    this.Quill.pasteHTML(this.currentValue);
    this.Quill.on("text-change", (delta, oldDelta, source) => {
      const html = this.$refs.editor.children[0].innerHTML;
      const text = this.Quill.getText();
      const quill = this.Quill;
      this.currentValue = html;
      this.$emit("input", html);
      this.$emit("on-change", { html, text, quill });
    });
    this.Quill.on("text-change", (delta, oldDelta, source) => {
      this.$emit("on-text-change", delta, oldDelta, source);
    });
    this.Quill.on("selection-change", (range, oldRange, source) => {
      this.$emit("on-selection-change", range, oldRange, source);
    });
    this.Quill.on("editor-change", (eventName, ...args) => {
      this.$emit("on-editor-change", eventName, ...args);
    });
  },
},
};
</script>

<style>
.editor, .ql-toolbar {
white-space: pre-wrap!important;
line-height: normal !important;
}
.quill-img {
display: none;
}
.ql-snow .ql-tooltip[data-mode="link"]::before {
content: "請輸入連結位址:";
}
.ql-snow .ql-tooltip.ql-editing a.ql-action::after {
border-right: 0px;
content: "保存";
padding-right: 0px;
}

.ql-snow .ql-tooltip[data-mode="video"]::before {
content: "請輸入影片位址:";
}

.ql-snow .ql-picker.ql-size .ql-picker-label::before,
.ql-snow .ql-picker.ql-size .ql-picker-item::before {
content: "14px";
}
.ql-snow .ql-picker.ql-size .ql-picker-label[data-value="small"]::before,
.ql-snow .ql-picker.ql-size .ql-picker-item[data-value="small"]::before {
content: "10px";
}
.ql-snow .ql-picker.ql-size .ql-picker-label[data-value="large"]::before,
.ql-snow .ql-picker.ql-size .ql-picker-item[data-value="large"]::before {
content: "18px";
}
.ql-snow .ql-picker.ql-size .ql-picker-label[data-value="huge"]::before,
.ql-snow .ql-picker.ql-size .ql-picker-item[data-value="huge"]::before {
content: "32px";
}

.ql-snow .ql-picker.ql-header .ql-picker-label::before,
.ql-snow .ql-picker.ql-header .ql-picker-item::before {
content: "文本";
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="1"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="1"]::before {
content: "標題1";
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="2"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="2"]::before {
content: "標題2";
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="3"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="3"]::before {
content: "標題3";
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="4"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="4"]::before {
content: "標題4";
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="5"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="5"]::before {
content: "標題5";
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="6"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="6"]::before {
content: "標題6";
}

.ql-snow .ql-picker.ql-font .ql-picker-label::before,
.ql-snow .ql-picker.ql-font .ql-picker-item::before {
content: "標準字體";
}
.ql-snow .ql-picker.ql-font .ql-picker-label[data-value="serif"]::before,
.ql-snow .ql-picker.ql-font .ql-picker-item[data-value="serif"]::before {
content: "襯線字體";
}
.ql-snow .ql-picker.ql-font .ql-picker-label[data-value="monospace"]::before,
.ql-snow .ql-picker.ql-font .ql-picker-item[data-value="monospace"]::before {
content: "等寬字體";
}
</style>