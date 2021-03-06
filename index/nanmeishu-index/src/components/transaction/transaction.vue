<template>
  <div id="transaction">
    <van-nav-bar
      title="代办事务"
      @click-right="showMenuAndOverlay"
      @click-left="showDateMenuAndOverlay"
      style="background: #f9f9f9"
    >
      <template #left>
        <span>{{myDate}}<span style="margin-left: 1.2vw"
                              class="icon-xiala iconfont"></span></span>
      </template>
      <template #right>
        <span id="gengduo"><span class="icon-gengduo iconfont"></span></span>
      </template>
    </van-nav-bar>
    <van-calendar v-model="showPicker" :min-date="new Date(2010,0,1)" :max-date="new Date(2030,0,1)"
                  @confirm="onConfirm"/>
    <bottom v-bind:tabIndex="1"></bottom>
    <van-list
      v-model="loading"
      :finished="finished"
      finished-text="没有更多了"
      @load="onLoad"
      id="outerList"
    >
      <van-checkbox-group v-model="commitList" id="outerGroup">
        <van-swipe-cell v-for="(transaction,index) in transactions" :key="transaction.transactionId"
                        v-show="(!noStatus1)||(transaction.statuss==0)">

          <template #right>
            <van-button square type="primary" :text="transaction.tops==0?'置顶':'取消置顶'" @click="setTop(transaction)"/>
            <van-button square type="danger" text="删除" @click="myDelete(transaction.transactionId,index)"/>
            <span style="width: 1vw;height: 100%"> </span>
          </template>
          <van-cell @click="toggle(index,transaction)">
            <template #icon>
              <van-checkbox :name="transaction.transactionId" ref="checkboxes"/>
            </template>
            <template #title>
              <span style="margin-left: 3vw;"
                    v-bind:class="{finishSpan:isStatuss(transaction)}">{{transaction.content}}</span>
            </template>
            <template #right-icon>
              <span style="margin-right: 3vw">{{showRightDate(transaction)}} <span class="iconfont icon-ziyuan"
                                                                                   v-show="transaction.tops!=0"
                                                                                   style="font-size:18px;color: red;"></span></span>
            </template>
          </van-cell>
        </van-swipe-cell>
      </van-checkbox-group>
    </van-list>
    <TransactionMenu id="TransactionMenu" @menu-type="menuType" v-show="showMenu"></TransactionMenu>
    <DateMenu id="DateMenu" @date-menu-type="dateMenuType" v-show="showDateMenu"></DateMenu>
    <van-overlay :show="showOverlay" @click="clonseOverlay" :custom-style="{background:'rgba(255,255,255,0.2)'}"/>
    <van-dialog v-model="showAdd" title="代办事务" show-cancel-button width="100vw" style="height: 60vh"
                :showConfirmButton="false" :showCancelButton="false">
      <addTransaction @exit_add="exitAdd"></addTransaction>
    </van-dialog>
    <van-dialog v-model="showSet" title="设置" show-cancel-button width="70vw" style="height: auto"
                :showCancelButton="false">
      <Settings @is_set="isSet"></Settings>
    </van-dialog>
  </div>
</template>

<script>
  import bottom from "../common/bottom";
  import {getById, update, deleteById} from "../api/transaction";
  import TransactionMenu from "./TransactionMenu";
  import DateMenu from "./DateMenu";
  import addTransaction from "./addTransaction";
  import Settings from "./Settings";

  export default {
    name: "transaction",
    components: {
      bottom,
      TransactionMenu,
      DateMenu,
      addTransaction,
      Settings
    },
    data() {
      return {
        showPicker: false,
        myDate: "今天",
        transactions: [],
        loading: false,
        finished: false,
        token: this.getCookie("token"),
        commitList: [],
        showMenu: false,
        showOverlay: false,
        showDateMenu: false,
        showAdd: false,
        showSet: false,
        noStatus1: false
      }
    },
    methods: {
      isSet(value) {
        this.noStatus1 = value;
      },
      exitAdd(value) {
        if (value == 1) {
          this.showAdd = false;
        }
      },
      //日期选择
      onConfirm(second) {
        let first = new Date();
        let date = null;
        if (first.getFullYear() === second.getFullYear() &&
          first.getMonth() === second.getMonth() &&
          first.getDate() === second.getDate()) {
          date = this.parseTime(second, "{y}-{m}-{d}");
          this.myDate = "今天";
        } else {
          date = this.parseTime(second, "{y}-{m}-{d}");
          this.myDate = date;
        }
        let params = {type: -1, startDate: date, status: 1};
        getById(params, {"accessToken": this.token})
          .then(res => {
            this.transactions = res.data.data;
            this.finished = true;
            this.transactions.map(transaction => {
              if (transaction.statuss == 1) {
                this.commitList[this.commitList.length] = transaction.transactionId;
              }
            })
          })
        this.showPicker = false;
      },
      setTop(transaction) {
        if (transaction.tops == 0) {
          //置顶操作
          transaction.tops = 1;
        } else {
          //取消置顶
          transaction.tops = 0;
        }
        update(transaction, {"accessToken": this.token})
          .then(res => {
            this.$toast.success("操作成功");
            this.$router.push({
              path: 'black',
              query: {url: '/transaction'}
            })
          })
      },
      clonseOverlay() {
        this.showMenu = false;
        this.showOverlay = false;
        this.showDateMenu = false;
      },
      showMenuAndOverlay() {
        this.showMenu = true;
        this.showOverlay = true;
      },
      showDateMenuAndOverlay() {
        this.showDateMenu = true;
        this.showOverlay = true;
      },
      showRightDate(transaction) {
        if (this.myDate == '今天') {
          return (transaction.startTime == '00:00:44' ? '无限制时间' : transaction.startTime);
        } else {
          return (transaction.startDate == '2000-01-01' ? '每日事务' : transaction.startDate) + " " +
            (transaction.startTime == '00:00:44' ? '无限制时间' : transaction.startTime)
        }
      },
      onLoad() {
        this.$toast.loading();
        let date = this.parseTime(new Date(), "{y}-{m}-{d}");
        getById({type: -1, startDate: date, status: 1}, {"accessToken": this.token})
          .then(res => {
            this.transactions = res.data.data;
            this.finished = true;
            this.transactions.map(transaction => {
              if (transaction.statuss == 1) {
                this.commitList[this.commitList.length] = transaction.transactionId;
              }
            })
            this.$toast.clear();
          })
      },
      myDelete(id, index) {
        this.$dialog.confirm({
          title: '提示',
          message: '您是否删除此事务?',
        })
          .then(() => {
            deleteById({transactionId: id}, {"accessToken": this.token})
              .then(res => {
                this.$toast.success("删除成功");
                this.transactions.splice(index, 1);
              })
          })
          .catch(() => {
            // on cancel
          });

      },
      toggle(index, transaction) {
        this.$refs.checkboxes[index].toggle();
        let commitDateTime = transaction.statuss == 0 ? new Date() : null;
        transaction.statuss = transaction.statuss == 0 ? 1 : 0;
        let data = {
          transactionId: transaction.transactionId,
          statuss: transaction.statuss,
          commitDateTime: commitDateTime
        };
        update(data, {"accessToken": this.token})
          .then(res => {
            if (transaction.statuss == 0) {
              let i;
              for (i = 0; i < this.commitList.length; i++) {
                if (transaction.transactionId == this.commitList[i]) {
                  break;
                }
              }
              this.commitList.splice(i, 1);
            }
          })
      },
      //判断状态是否已完成
      isStatuss(transaction) {
        if (transaction.statuss == 1) {
          return true;
        } else {
          return false;
        }
      },
      menuType(data) {
        if (data == 1) {
          this.showAdd = true;
        } else {
          this.showSet = true;
        }
      },
      dateMenuType(date) {
        let falg = false;
        switch (date) {
          case 1: {
            if (this.myDate != '今天') {
              this.myDate = '今天';
              falg = true;
            }
          }
            break;
          case 2: {
            if (this.myDate != '明天') {
              this.myDate = '明天';
              falg = true;
            }
          }
            break;
          case 3: {
            if (this.myDate != '本周') {
              this.myDate = '本周';
              falg = true;
            }
          }
            break;
          case 4: {
            if (this.myDate != '本月') {
              this.myDate = '本月';
              falg = true;
            }
          }
            break;
          case 5: {
            if (this.myDate != '全部') {
              this.myDate = '全部';
              falg = true;
            }
          }
            break;
          case 6: {
            this.showPicker = true;
          }
            break;
        }
        if (falg) {
          this.getByIdAndS(date);
        }
      },
      //获取指定日期的事务
      getByIdAndS(status) {
        let date = this.parseTime(new Date(2021, 0, 28), "{y}-{m}-{d}");
        let params = {type: -1, startDate: date, status: status};
        getById(params, {"accessToken": this.token})
          .then(res => {
            this.transactions = res.data.data;
            this.finished = true;
            this.transactions.map(transaction => {
              if (transaction.statuss == 1) {
                this.commitList[this.commitList.length] = transaction.transactionId;
              }
            })
          })
      }
    }
  }
</script>

<style scoped>
  @import "../../assets/font/iconfont.css";

  #addClick {
    position: fixed;
    bottom: 8vh;
    left: 0;
  }

  #outerList {
    width: 100vw;
    height: 84vh;
    overflow: hidden;
  }

  #outerGroup {
    width: 102%;
    height: 100%;
    overflow-y: scroll;
  }

  .finishSpan {
    text-decoration: line-through;
    color: rgba(0, 0, 0, 0.5)
  }

  #TransactionMenu {
    position: fixed;
    right: 4vw;
    top: 5vh;
    z-index: 10;
  }

  #DateMenu {
    position: fixed;
    left: 2vw;
    top: 6vh;
    z-index: 10;
  }

</style>
