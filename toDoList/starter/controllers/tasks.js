const Task = require("../models/Task");
const asyncWrapper = require("../middleware/async");
const { createCustomError } = require("../errors/custom-errors");

const getAllTasks = asyncWrapper(async (req, res) => {
  const tasks = await Task.find({});
  res.status(200).json({ tasks });
});

const createTask = asyncWrapper(async (req, res) => {
  const task = await Task.create(req.body);
  res.status(201).json({task});
});

const getTask = asyncWrapper(async (req, res) => {
  const { id: taskID } = req.params; // destructuration pour extraire la valeur de id dans req.params et la stocker dans taskId
  const task = await Task.findOne({ _id: taskID });
  if (!task) {
    return next(createCustomError(`No task with id: ${taskID}`, 404));
  }
  res.status(200).json({ id: req.params.id });
});

const updateTask = asyncWrapper(async (req, res) => {
  const { id: taskID } = req.params;
  const task = await Task.findOneAndUpdate({ _id: taskID }, req.body, {
    new: true,
    runValidators: true,
  });
  if (!task) {
    return next(createCustomError(`No task with id: ${taskID}`, 404));
  }
  res.status(200).json({ task });
});

const deleteTask = asyncWrapper(async (req, res) => {
  // async promise that the await function (that takes time) will be complete before continuing
  const { id: taskID } = req.params; // taskId take tha value of the Id from the url
  const task = await Task.findOneAndDelete({ _id: taskID }); // await come along with async (need to find id in the DB: heavy)
  if (!task) {
    return res.status(404).json({ msg: `No found task with id ${taskID}` });
  }
  res.status(200).json({ msg: `Task ${taskId} has been deleted` });
});

const editTask = asyncWrapper(async (req, res) => {
  const { id: taskID } = req.params;
  const task = await Task.findOneAndUpdate({ _id: taskID }, req.body, {
    new: true,
    runValidators: true,
    overwrite: true,
  });
  if (!task) {
    return res
      .status(404)
      .json({ msg: `Error: couldn't find the id: ${taskID}` });
  }
  res.status(200).json({ task });
});

module.exports = {
  getAllTasks,
  createTask,
  getTask,
  updateTask,
  deleteTask,
  editTask,
};
