
<template>
  <div>
    <h2 id="ConR">
      Conference Rooms
    </h2>
    <el-table :data="Rooms" style="width: 80%;;margin: 0 auto;">
      <el-table-column prop="Room Name" label="Room Name"/>
      <el-table-column prop="Department" label="Department"/>
      <el-table-column prop="Type" label="Type"/>
      <el-table-column prop="Location" label="Location"/>
      <el-table-column prop="Date" label="Date"/>
      <el-table-column prop="Start Time" label="Start Time"/>
      <el-table-column prop="End Time" label="End Time"/>
      <el-table-column prop="Max Duration" label="Max Duration"/>
      <el-table-column prop="Operation" >
      <template #default="scope" >
<!--        type="info" style="margin-left: 45%;font-size: 14px; margin-top: 20px"-->
        <el-button class="deleteAndEdit"  @click="deleteRoom(scope.$index,scope.row['Room Name'])">
        delete
        </el-button >
        <el-button class="deleteAndEdit" @click="editData(scope.$index)">
          edit
        </el-button>
      </template>
        </el-table-column>
    </el-table>
    <el-button style="margin-left: 45%; margin-top: 20px" type="primary" @click="createNewRoom">
      Add Room
    </el-button>

    <el-dialog  title="A new Room" :visible.sync="newRoomDialog" width=40% >
      <el-form
      ref="RoomForm"
      :model="RoomForm"
      :rules="rules"
      label-width="auto"
      label-position="right"
      size="default"
      >
        <el-form-item label="Room Name" prop="RoomName">
          <el-input v-model="RoomForm.RoomName"/>
        </el-form-item>

        <el-form-item label="Department" prop="Department">
          <el-input v-model="RoomForm.Department"/>
        </el-form-item>

        <el-form-item label="Type" prop="Type">
          <el-button-group>
            <el-button type="radio"
                       :class="{'highlighted':RoomForm.Type==='small'}"
                       @click="selectType('small')" >
              small</el-button>
            <el-button type="radio"
                       :class="{'highlighted':RoomForm.Type==='medium'}"
                       @click="selectType('medium')">
              medium</el-button>
            <el-button type="radio"
                       :class="{'highlighted':RoomForm.Type==='big'}"
                       @click="selectType('big')">
              big</el-button>
          </el-button-group>
        </el-form-item>

        <el-form-item label="Location" prop="Location">
          <el-select v-model="RoomForm.Building">
            <el-option label="Teaching Building No.1 Lecture Hall" value="Teaching Building No.1 Lecture Hall"></el-option>
            <el-option label="Research Building Lecture Hall" value="Research Building Lecture Hall"></el-option>
            <el-option label="Library Conference Hall" value="Library Conference Hall"></el-option>
            <el-option label="South Building" value="South Building"></el-option>
          </el-select>
          <el-input v-model="RoomForm.room"/>
        </el-form-item>

        <el-form-item label="Date" prop="Date">
<!--          日期格式必须为yyyy/MM/dd而不是yyyy/mm/dd,不然月份显示会出现bug-->
          <el-date-picker
            value-format='yyyy/MM/dd'
            v-model="RoomForm.Date"
            type="date"
            label="Pick a date"
            placeholder="Pick a date"
            style="width: 100%"
          />
        </el-form-item>

<!--        prop一定要和v-model绑定的属性名称完全相同！！！-->
        <el-form-item label="Start Time" prop="StartTime">
          <el-time-picker
            format='HH:mm'
            value-format='HH:mm'
            v-model="RoomForm.StartTime"
            label="Pick a time"
            placeholder="Pick a time"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="End Time" prop="EndTime">
          <el-time-picker
            format='HH:mm'
            value-format='HH:mm'
            v-model="RoomForm.EndTime"
            label="Pick a time"
            placeholder="Pick a time"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="Max Duration" prop="MaxDuration">
          <el-input v-model="RoomForm.MaxDuration"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="AddNewRoom()">标注新的可使用房间</el-button>
        </el-form-item>

      </el-form>
    </el-dialog>


    <el-dialog  title="edit a Room" :visible.sync="editRoomDialog" width=40% >
      <el-form
        ref="RoomForm"
        :model="RoomForm"
        :rules="rules"
        label-width="auto"
        label-position="right"
        size="default"
      >
        <el-form-item label="Room Name" prop="RoomName">
          <el-input v-model="RoomForm.RoomName"/>
        </el-form-item>

        <el-form-item label="Department" prop="Department">
          <el-input v-model="RoomForm.Department"/>
        </el-form-item>

        <el-form-item label="Type" prop="Type">
          <el-button-group>
            <el-button type="radio"
                       :class="{'highlighted':RoomForm.Type==='small'}"
                       @click="selectType('small')" >
              small</el-button>
            <el-button type="radio"
                       :class="{'highlighted':RoomForm.Type==='medium'}"
                       @click="selectType('medium')">
              medium</el-button>
            <el-button type="radio"
                       :class="{'highlighted':RoomForm.Type==='big'}"
                       @click="selectType('big')">
              big</el-button>
          </el-button-group>
        </el-form-item>

        <el-form-item label="Location" prop="Location">
          <el-select v-model="RoomForm.Building">
            <el-option label="Teaching Building No.1 Lecture Hall" value="Teaching Building No.1 Lecture Hall"></el-option>
            <el-option label="Research Building Lecture Hall" value="Research Building Lecture Hall"></el-option>
            <el-option label="Library Conference Hall" value="Library Conference Hall"></el-option>
            <el-option label="South Building" value="South Building"></el-option>
          </el-select>
          <el-input v-model="RoomForm.room"/>
        </el-form-item>

        <el-form-item label="Date" prop="Date">
          <!--          日期格式必须为yyyy/MM/dd而不是yyyy/mm/dd,不然月份显示会出现bug-->
          <el-date-picker
            value-format='yyyy/MM/dd'
            v-model="RoomForm.Date"
            type="date"
            label="Pick a date"
            placeholder="Pick a date"
            style="width: 100%"
          />
        </el-form-item>

        <!--        prop一定要和v-model绑定的属性名称完全相同！！！-->
        <el-form-item label="Start Time" prop="StartTime">
          <el-time-picker
            format='HH:mm'
            value-format='HH:mm'
            v-model="RoomForm.StartTime"
            label="Pick a time"
            placeholder="Pick a time"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="End Time" prop="EndTime">
          <el-time-picker
            format='HH:mm'
            value-format='HH:mm'
            v-model="RoomForm.EndTime"
            label="Pick a time"
            placeholder="Pick a time"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="Max Duration" prop="MaxDuration">
          <el-input v-model="RoomForm.MaxDuration"/>
        </el-form-item>
        <el-form-item>
          <el-button @click="editRoom()">edit</el-button>
        </el-form-item>
<!--        // <template #default="scope" >-->
<!--     type="info" style="margin-left: 45%;font-size: 14px; margin-top: 20px"&ndash;&gt;-->
<!--        <el-button class="deleteAndEdit"  @click="deleteRoom(scope.$index,scope.row['Room Name'])">-->
<!--          delete-->
<!--        </el-button >-->
<!--        <el-button class="deleteAndEdit" @click="editData(scope.$index)">-->
<!--          edit-->
<!--        </el-button>-->
<!--      </template>-->

      </el-form>
    </el-dialog>


  </div>
</template>

<script>




  export default {
    name: "ConferenceRooms",
    data() {
      const validateRoomName=(rule,value,callback)=>{
        const regex= /^(?=.*[a-zA-Z])(?=.*\d).+$/;
        if(!value){
         return callback(new Error('This field must be filled in'));
        }
        if(!regex.test(value)) {
         return callback(new Error('invalid'));
        }
        return callback();
      };
      const validateDepartment=(rule,value,callback)=>{
        const regex=/^[a-zA-Z\s]+$/;
        if(!value){
         return callback(new Error('This field must be filled in'));
        }
        if(!regex.test(value)) {
         return callback(new Error('Department name can only be English letters'));
        }
        return callback();
      }
      const validateDate=(rule,value,callback)=>{
        const chosenDate=new Date(value);
        const currentDate=new Date();
        // console.log('value',value);
        // console.log('chosenDate:', chosenDate);
        // console.log('currentDate:', currentDate);
          // return callback(new Error(chosenDate.toDateString()+"ddd"));
        if(!value){
          return callback(new Error('This field must be filled in'));
        }
        if(chosenDate<currentDate){
          return callback(new Error('Time travel is not allowed'));
        }
        return callback();
      };

      const validateStartTime=(rule,value,callback)=>{
        console.log('value StartTime',value);
        if(!value){
          return callback(new Error('This field must be filled in'));
        }
         return callback();
      };
      const validateEndTime=(rule,value,callback)=>{
        const StartTime=new Date('2000-01-01 '+this.RoomForm.StartTime);
        // console.log('this.RoomForm.StartTime',StartTime);
        const EndTime=new Date('2000-01-01 '+value);
        // console.log('value',value);
        if(!value||!this.RoomForm.StartTime){
          return callback(new Error('This field and Start Time must be filled in'));
        }
        if(StartTime>=EndTime){
          return callback(new Error('Start time should no later than the end time'));
        }
        return callback();
      };
      const validateMaxDuration=(rule,value,callback)=>{
        console.log('value',value);
        if(!value){
          return callback(new Error('This field must be filled in'));
        }
        const regex=/^[0-9]+$/;
        if(!regex.test(value)){
          return callback(new Error('Duration must be a number'));
        }
        return callback();
      };
      const BuildLocation=(rule,value,callback)=>{
        this.TheLocation();
        return callback();
      }


      return {
        Rooms: [
          {
            "Room Name": "Room1",
            "Department": "Electrical",
            "Type": "small",
            "Location": "South Building 426A",
            "Building":"South Building",
            "room":"426A",
            "Date": "2023/9/10",
            "Start Time": "08:00",
            "End Time": "20:00",
            "Max Duration": "2h"
          },
          {
            "Room Name": "Room2",
            "Department": "Computer Science",
            "Type": "big",
            "Location": "South Building 434A",
            "Building":"South Building",
            "room":"434A",
            "Date": "2023/9/10",
            "Start Time": "00:00",
            "End Time": "24:00",
            "Max Duration": "4h"
          }

        ],
        RoomForm: {
          RoomName: "",
          Department: "",
          Type: "",
          Location: "",
          Building:"",
          room:"",
          Date: "",
          StartTime: "",
          EndTime: "",
          MaxDuration: ""
        },
        rules: {
          //区别好require与required
          RoomName: [
            {required: true, validator:validateRoomName,trigger:'blur'}
          ],
          Department: [//此处如果添加了message会导致validator中的callback()中的提示内容无法显示
            {required: true, validator:validateDepartment,trigger: "blur"}
          ],
          Type: [
            {required: true, trigger: "blur"}
          ],
          Location: [
            {required: true,validator:BuildLocation, trigger: "blur"}
          ],
          Building: [
            {required: true, trigger: "blur"}
          ],
          room:[
            {required: true, trigger: "blur"}
          ],
          Date: [
            {required: true, validator:validateDate,trigger: "blur"}
          ],
          StartTime: [
            {required: true,validator:validateStartTime, trigger: "blur"}
          ],
          EndTime: [
            {required: true, validator:validateEndTime,trigger: "blur"}
          ],
          MaxDuration: [
            {required: true, validator:validateMaxDuration,trigger: "blur"}
          ]
        },
        newRoomDialog:false,
        editRoomDialog:false,
        editIndex:-1,
      }
    },
    methods: {

//是methods不是method
      createNewRoom(){
        //通过调整对话框是否可见实现点击按钮后出现对话框
        this.newRoomDialog=true;
        this.brushForm();
      },
      deleteRoom(index,RN) {
        this.Rooms.splice(index, 1);
        alert("已取消" + RN+ "的预约");
      },
      selectType(chosenType){
        this.RoomForm.Type=chosenType;
      },
      AddNewRoom() {
        console.log(this.RoomForm.Date);
        this.$refs.RoomForm.validate((valid) => {
          if (valid) {
            this.Rooms.push({//更新表格数据
              "Room Name": this.RoomForm.RoomName,
              "Department": this.RoomForm.Department,
              "Type": this.RoomForm.Type,
              "Location": this.RoomForm.Location,
              "Date": this.RoomForm.Date,
              "Start Time": this.RoomForm.StartTime,
              "End Time": this.RoomForm.EndTime,
              "Max Duration": this.RoomForm.MaxDuration+'h'
            })
            alert('submit!')
            this.newRoomDialog = false;
            this.RoomForm.Building="";
            this.RoomForm.room="";
          } else {
            console.log("Add Fail");
          }
        })
      },
      editRoom() {
        console.log(this.RoomForm.Date);
        this.$refs.RoomForm.validate((valid) => {
          if (valid) {
            const updatedRoom={//更新表格数据
              "Room Name": this.RoomForm.RoomName,
              "Department": this.RoomForm.Department,
              "Type": this.RoomForm.Type,
              "Location": this.RoomForm.Location,
              "Date": this.RoomForm.Date,
              Building:this.RoomForm.Building,
              room:this.RoomForm.room,
              "Start Time": this.RoomForm.StartTime,
              "End Time": this.RoomForm.EndTime,
              "Max Duration": this.RoomForm.MaxDuration+'h'
            };
            this.Rooms.splice(this.editIndex,1,updatedRoom);
            alert('submit!')
            this.editRoomDialog= false
            // this.RoomForm.Building="";
            // this.RoomForm.room="";
          } else {
            console.log("Edit Fail");
          }
        })
      },
      brushForm(){//实在想不到更好的刷新表单的方式咧
        this.RoomForm={
          RoomName: "",
            Department: "",
            Type: "",
            Location: "",
            Building:"",
            room:"",
            Date: "",
            StartTime: "",
            EndTime: "",
            MaxDuration: ""
        }
      },
      TheLocation(){
        this.RoomForm.Location=this.RoomForm.Building+' '+this.RoomForm.room;
        // this.RoomForm.Building="";
        // this.RoomForm.room="";
      },
      editData(index){
        this.editIndex=index;
        console.log("has get "+this.Rooms[index].room);
        console.log("RoomName"+this.Rooms[index]["Room Name"]);
        this.editRoomDialog=true;
        this.RoomForm.RoomName=this.Rooms[index]["Room Name"];
        this.RoomForm.Type=this.Rooms[index].Type;
        this.RoomForm.Date=this.Rooms[index].Date;
        this.RoomForm.Building=this.Rooms[index].Building;
        this.RoomForm.room=this.Rooms[index].room;
        this.RoomForm.MaxDuration=this.Rooms[index]["Max Duration"].slice(0,-1);
        this.RoomForm.StartTime=this.Rooms[index]["Start Time"];
        this.RoomForm.EndTime=this.Rooms[index]["End Time"];
        this.RoomForm.Department=this.Rooms[index].Department;
      }

    }
  }

</script>


<style scoped>
#ConR{
  text-align: center;
  font-size: 24px;

  //font-style: italic;
  margin: 20px;
}

.highlighted{
  background-color: #eee176;
}
.deleteAndEdit{
  margin-left: 10px;
  margin-bottom: 5px;
  padding: 8px;
  width: 65px;
  font-size:14px;
  background-color: #eeab64;
}
</style>
