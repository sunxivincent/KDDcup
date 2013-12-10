% generate randomly shuffled data from training and testing dataset 
% then save as all.dat 

test = load('phy_test_p.dat');
disp('done importing test data');
train = load('phy_train.dat');
disp('done importing training data');
all = [test; train];
all = all(randperm(size(all,1)),:);
save('shuffle_.dat', 'all','-ascii'); % save data
disp('done generating data');