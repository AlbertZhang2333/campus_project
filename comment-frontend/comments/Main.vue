<template>
  <div>
    <el-card>
      <el-form :model="commentForm" ref="commentForm" size="small">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="commentForm.userName"></el-input>
        </el-form-item>
        <el-form-item label="用户邮箱" prop="username">
          <el-input v-model="commentForm.userMail"></el-input>
        </el-form-item>
        <el-form-item label="评论内容" prop="content">
          <el-input type="textarea" v-model="commentForm.comment"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitComment">发表评论</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-divider></el-divider>

    <el-timeline>
      <el-timeline-item v-for="(comment, index) in comments" :key="index">
        <el-card>
          <p>{{ comment.userName }}：</p>
          <el-card>
            <p>{{ comment.comment }}</p>
          </el-card>
          <p>评论时间：{{ comment.date + ' ' + comment.time }}</p> <!-- 使用管道格式化时间 -->
          <el-button @click="deleteComment(index)" type="danger" size="mini" style="margin-top: 5px; ">删除</el-button>
          <el-button @click="toggleReplyMode(index)" type="primary" size="mini" style="margin-top: 5px;">
            {{ isReplyMode[index] ? '取消回复' : '回复' }}
          </el-button>

        </el-card>
        <!-- 回复列表 -->
      </el-timeline-item>
    </el-timeline>
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[5, 10, 20, 30]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
  </div>
</template>

<script>
import {date, time} from "mockjs/src/mock/random/date";
import {int} from "mockjs/src/mock/random/basic";

export default {
  name: "Main",
  data() {
    return {
      commentForm: {
        id: int, // 初始为0，具体情况视需求而定
        userName: "",
        userMail: "",
        comment: "",
        vis: true, // 初始为false，具体情况视需求而定
        time: '', // 如果你需要设置时间，可以在提交时在后端进行处理，或者在前端使用合适的格式
        date: '', // 同样，如果你需要设置日期，可以在提交时在后端进行处理，或者在前端使用合适的格式
        belongDepartment: 0,
      },
      comments: [],
      isReplyMode: [], // 用于跟踪是否处于回复模式
      replyContent: [], // 存储回复的内容

      pageSize: 5,
      currentPage: 1,
      total: 0
    };
  },
  methods: {
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageSize = val
      this.currentPage = 1
      this.loadPost()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.currentPage = val
      this.loadPost()
    },
    loadPost() {
      this.$axios.post(this.$httpUrl + 'Comment/allComments', null, {
        params: {
          pageSize: this.pageSize,
          currentPage: this.currentPage
        }
      }).then(res => res.data).then(res => {
            console.log(res)
            // console.log([res.data])
            if (res.code === 200) {
              this.comments = res.data
              // console.log(this.comments)
              this.total = res.total
            } else {
              this.$message.warning('数据加载失败!');
            }
          }
      )
    },
    clearForm() {
      this.commentForm = {
        id: int, // 初始为0，具体情况视需求而定
        userName: "",
        userMail: "",
        comment: "",
        vis: true, // 初始为false，具体情况视需求而定
        time: '', // 如果你需要设置时间，可以在提交时在后端进行处理，或者在前端使用合适的格式
        date: '', // 同样，如果你需要设置日期，可以在提交时在后端进行处理，或者在前端使用合适的格式
        belongDepartment: 0,
      }
    },
    submitComment() {
      if (this.commentForm.comment !== "") {
        const date = new Date(); // 获取当前时间戳
        this.commentForm.time = date.getHours().toString() + ':' + date.getMinutes().toString() + ':' + date.getSeconds().toString();
        this.commentForm.date = date.getFullYear().toString() + '-' + (date.getMonth() + 1).toString() + '-' + date.getDate();
        this.commentForm.comment = this.commentForm.comment.toString();

        // console.log(this.commentForm.comment)

        this.$axios.post(this.$httpUrl + 'Comment/commentRecord', this.commentForm).then(res => res.data).then(res => {
          console.log(res)
          this.$message({
            message: '评论发表成功!',
            type: 'success'
          });
          this.isReplyMode.push(false);
          this.clearForm();
          this.loadPost();
        })
            .catch(error => {
              console.error('Error adding comment:', error);
              this.$message.error('评论发表失败，请重试!');
            });


      } else {
        this.$message.warning("请输入回复内容");
      }
    },

    deleteComment(index) {
      const commentId = this.comments[index].id;

      console.log(index)
      console.log(this.comments[index])

      console.log(this.comments)

      this.$axios.delete(`${this.$httpUrl}Comment/commentDelete/${commentId}`)
          .then(res => res.data)
          .then(res => {
            console.log(res);
            this.$message({
              message: '删除成功!',
              type: 'success'
            });
            this.loadPost();
          })
          .catch(error => {
            console.error('Error deleting comment:', error);
            this.$message.error('删除失败，请重试!');
          });

      this.loadPost();
    },
    toggleReplyMode(index) {
      // 切换回复模式
      this.$set(this.isReplyMode, index, !this.isReplyMode[index]);
    },
    replyComment(index) {
      if (this.replyContent[index]) {
        const date = new Date();
        this.comments[index].replies.push({
          username: this.commentForm.username,
          comment: this.replyContent[index],
          date: date,
        });
        this.replyContent[index] = "";
        this.isReplyMode[index] = false;
      } else {
        this.$message.warning("请输入回复内容");
      }
    }
  },
  beforeMount() {
    this.loadPost()
  },
  filters: {
    formatDate(timestamp) {
      // 管道函数，用于格式化时间戳
      const date = new Date(timestamp);
      return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()} ${date.getHours()}:${date.getMinutes()}:${date.getSeconds()}`;
    }
  }
};
</script>

<style scoped>
/* 自定义样式可以放在这里 */
</style>
