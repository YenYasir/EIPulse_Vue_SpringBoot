<!-- 支持三級以上菜單快取 -->
<template>
  <section class="app-main">
    <transition name="fade-transform" mode="out-in">
      <keep-alive :max="20" :exclude="notCacheName">
        <router-view :key="key" />
      </keep-alive>
    </transition>
  </section>
</template>

<script>
import Global from "@/layout/components/global.js";

export default {
  name: 'AppMain',
  computed: {
    notCacheName() {
      var visitedViews = this.$store.state.tagsView.visitedViews;
      var noCacheViews = [];
      Object.keys(visitedViews).some((index) => {
        if (visitedViews[index].meta.noCache) {
          noCacheViews.push(visitedViews[index].name);
        }
      });
      return noCacheViews;
    },
    key() {
      return this.$route.path;
    },
  },
  mounted() {
    // 關閉標籤觸發
    Global.$on("removeCache", (name, view) => {
      this.removeCache(name, view);
    });
  },
  methods: {
    // 獲取有keep-alive子節點的Vnode
    getVnode() {
      // 判斷子集非空
      if (this.$children.length == 0) return false;
      let vnode;
      for (let item of this.$children) {
        // 如果data中有key則代表找到了keep-alive下面的子集，這個key就是router-view上的key
        if (item.$vnode.data.key) {
          vnode = item.$vnode;
          break;
        }
      }
      return vnode ? vnode : false;
    },
    // 移除keep-alive快取
    removeCache(name, view = {}) {
      let vnode = this.getVnode();
      if (!vnode) return false;
      let componentInstance = vnode.parent.componentInstance;
      // 這個key是用來獲取前綴用來後面正則匹配用的
      let keyStart = vnode.key.split("/")[0];
      let thisKey = `${keyStart}${view.fullPath}`;
      let regKey = `${keyStart}${view.path}`;

      this[name]({ componentInstance, thisKey, regKey });
    },
    // 移除其他
    closeOthersTags({ componentInstance, thisKey }) {
      Object.keys(componentInstance.cache).forEach((key, index) => {
        if (key != thisKey) {
          // 銷毀實例(這裡存在多個key指向一個快取的情況可能前面一個已經清除掉了所有要加判斷)
          if (componentInstance.cache[key]) {
            componentInstance.cache[key].componentInstance.$destroy();
          }
          // 刪除快取
          delete componentInstance.cache[key];
          // 移除key中對應的key
          componentInstance.keys.splice(index, 1);
        }
      });
    },
    // 移除所有快取
    closeAllTags({ componentInstance }) {
      // 銷毀實例
      Object.keys(componentInstance.cache).forEach((key) => {
        if (componentInstance.cache[key]) {
          componentInstance.cache[key].componentInstance.$destroy();
        }
      });
      // 刪除快取
      componentInstance.cache = {};
      // 移除key中對應的key
      componentInstance.keys = [];
    },
    // 移除單個快取
    closeSelectedTag({ componentInstance, regKey }) {
      let reg = new RegExp(`^${regKey}`);
      Object.keys(componentInstance.cache).forEach((key, i) => {
        if (reg.test(key)) {
          // 銷毀實例
          if (componentInstance.cache[key]) {
            componentInstance.cache[key].componentInstance.$destroy();
          }
          // 刪除快取
          delete componentInstance.cache[key];
          // 移除key中對應的key
          componentInstance.keys.splice(i, 1);
        }
      });
    },
    // 刷新單個快取
    refreshSelectedTag({ componentInstance, thisKey }) {
      Object.keys(componentInstance.cache).forEach((key, index) => {
        if (null != thisKey && key.replace("/redirect", "") == thisKey) {
          // 1 銷毀實例(這裡存在多個key指向一個快取的情況可能前面一個已經清除掉了所有要加判斷)
          if (componentInstance.cache[key]) {
            componentInstance.cache[key].componentInstance.$destroy();
          }
          // 2 刪除快取
          delete componentInstance.cache[key];
          // 3 移除key中對應的key
          componentInstance.keys.splice(index, 1);
        }
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.app-main {
  /* 50= navbar  50  */
  min-height: calc(100vh - 50px);
  width: 100%;
  position: relative;
  overflow: hidden;
}

.fixed-header + .app-main {
  padding-top: 50px;
}

.hasTagsView {
  .app-main {
    /* 84 = navbar + tags-view = 50 + 34 */
    min-height: calc(100vh - 84px);
  }

  .fixed-header + .app-main {
    padding-top: 84px;
  }
}
</style>

<style lang="scss">
// fix css style bug in open el-dialog
.el-popup-parent--hidden {
  .fixed-header {
    padding-right: 15px;
  }
}
</style>
