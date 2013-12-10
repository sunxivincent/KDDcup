# imputation on missing data
data = read.table('/Users/zhuoer/Documents/MATLAB/shuffle_.dat')

# coerce to NA
data[,c(22:24,46:48)][modi[,c(22:24,46:48)] == 999] = NA
data[,c(31,57)][data[,c(31,57)] == 9999] = 0 # only zero and NA, no need to perform any algorithm, replace NA with zero

c999 = data[,c(22:24,46:48)]
IMP = mi(c999, mi.info(c999), n.imp = 2, n.iter = 6) # ideally, n.imp >= 3, n.iter >= 10

a = IMP@imp # with multiple chain, create empty list
chain_1 = a$Chain1
chain_2 = a$Chain2

# with multiple chain, assess list operation
V22_sub1 = chain_1$V22@random
V23_sub1 = chain_1$V23@random
V24_sub1 = chain_1$V24@random       
V46_sub1 = chain_1$V46@random
V47_sub1 = chain_1$V47@random
V48_sub1 = chain_1$V48@random

V22_sub2 = chain_2$V22@random
V23_sub2 = chain_2$V23@random
V24_sub2 = chain_2$V24@random       
V46_sub2 = chain_2$V46@random
V47_sub2 = chain_2$V47@random
V48_sub2 = chain_2$V48@random

V22_sub = (V22_sub1+V22_sub2)/2
V23_sub = (V23_sub1+V23_sub2)/2
V24_sub = (V24_sub1+V24_sub2)/2
V46_sub = (V46_sub1+V46_sub2)/2
V47_sub = (V47_sub1+V47_sub2)/2
V48_sub = (V48_sub1+V48_sub2)/2

# more efficient with matrix manipulation
data[data[,22]==999,22] = V22_sub
data[data[,23]==999,23] = V23_sub
data[data[,24]==999,24] = V24_sub
data[data[,46]==999,46] = V46_sub
data[data[,47]==999,47] = V47_sub
data[data[,48]==999,48] = V48_sub

# output data
write.table(data,'/Users/zhuoer/Documents/MATLAB/mi_data.dat', sep = " ", row.names = FALSE, col.names = FALSE)
write.table(data,'/Users/zhuoer/Documents/MATLAB/mi_data.txt', sep = " ", row.names = FALSE, col.names = FALSE)